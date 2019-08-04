package com.example.bankingsystemandroid;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class RateUs extends BaseActivity {
    private Button addRating;
    AlertDialog.Builder alertDialogBuilder;
    private EditText userReview;
    private ImageView myImageView;
    private TextView myTextView;
    private Bitmap myBitmap;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Toast.makeText(this, ""+userReview, Toast.LENGTH_SHORT).show();
        myTextView = findViewById(R.id.textView);
        myImageView = findViewById(R.id.imageView);
        userReview = findViewById(R.id.userReview);
        addRating = findViewById(R.id.addRating);
        addRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myTextView.getText().toString().trim().isEmpty()){
                    showAlert(RateUs.this,"Error","Couldn't trace any face. Try Again With a New Image.");
                }
                else if(userReview.getText().toString().trim().isEmpty()){
                    showAlert(RateUs.this,"Error","Empty Review Field");
                }
                else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("reviews");
                    String id = myRef.push().getKey();
                    String secondID = myRef.push().getKey();


                    String rating = myTextView.getText().toString().trim();
                    String reviewMessage = userReview.getText().toString();
                    Toast.makeText(RateUs.this, "start"+myRef, Toast.LENGTH_SHORT).show();
                    myRef.child(id).child("email").child(secondID).child("rating").setValue(rating);
                    //myRef.child(id).child(userEmail).child(secondID).child("rating").setValue(rating);
                    //myRef.child(id).child(userEmail).child(secondID).child("review").setValue(reviewMessage);
                    startActivity(new Intent(RateUs.this,EmployeeHome.class));
                }
            }
        });
    }
    private void showAlert(Context context,String title,String message){
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                context);

        alertDialog.setTitle("ERROR");

        alertDialog.setMessage(message);


        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case WRITE_STORAGE:
                    checkPermission(""+requestCode,requestCode,requestCode);
                case CAMERA:
                    checkPermission(""+requestCode,requestCode,requestCode);
                    break;
                case SELECT_PHOTO:
                    Uri dataUri = data.getData();
                    String path = MyHelper.getPath(this, dataUri);
                    if (path == null) {
                        myBitmap = MyHelper.resizePhoto(photoFile, this, dataUri, myImageView);
                    } else {
                        myBitmap = MyHelper.resizePhoto(photoFile, path, myImageView);
                    }
                    if (myBitmap != null) {
                        myTextView.setText(null);
                        myImageView.setImageBitmap(myBitmap);
                        runFaceDetector(myBitmap);
                    }
                    break;
                case TAKE_PHOTO:
                    myBitmap = MyHelper.resizePhoto(photoFile, photoFile.getPath(), myImageView);
                    if (myBitmap != null) {
                        myTextView.setText(null);
                        myImageView.setImageBitmap(myBitmap);
                        runFaceDetector(myBitmap);
                    }
                    break;
            }
        }
    }

    private void runFaceDetector(Bitmap bitmap) {

//Create a FirebaseVisionFaceDetectorOptions object//

        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()

//Set the mode type; I’m using FAST_MODE//
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                //.setModeType(FirebaseVisionFaceDetectorOptions.FAST_MODE)

//Run additional classifiers for characterizing facial features//
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                //.setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)

//Detect all facial landmarks//
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                //.setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)

//Set the smallest desired face size//

                .setMinFaceSize(0.1f)

//Disable face tracking//

                //.setTrackingEnabled(false)
                .build();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(myBitmap);
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> faces) {
                myTextView.setText(runFaceRecog(faces));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure
                    (@NonNull Exception exception) {
                Toast.makeText(RateUs.this,
                        "Exception", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String runFaceRecog(List<FirebaseVisionFace> faces) {
        StringBuilder result = new StringBuilder();
        float smilingProbability = 0;
        float rightEyeOpenProbability = 0;
        float leftEyeOpenProbability = 0;

        for (FirebaseVisionFace face : faces) {

//Retrieve the probability that the face is smiling//

            if (face.getSmilingProbability() !=

//Check that the property was not un-computed//

                    FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                smilingProbability = face.getSmilingProbability();
            }

//Retrieve the probability that the right eye is open//

            if (face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                rightEyeOpenProbability = face.getRightEyeOpenProbability ();
            }

//Retrieve the probability that the left eye is open//

            if (face.getLeftEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                leftEyeOpenProbability = face.getLeftEyeOpenProbability();
            }

//Print “Smile:” to the TextView//

            smilingProbability = smilingProbability*100;
            int smile = (int) smilingProbability;
            result.append("Rating : "+smile+ " %");
//If the probability is 0.5 or higher...//
            /*
            if(smilingProbability < 0.25){
                result.append("No \nRating: " + smile);
            }
            else if(smilingProbability < 0.5){
                result.append("Yes \nRating: " + smile);
            }
            else if (smilingProbability < 0.7) {

//...print the following//

                result.append("Yes \nRating: " + smile);

//If the probability is 0.4 or lower...//

            }
            else  if (smilingProbability < 1){
                result.append("Yes \nRating: " + smile);
            }
            else {

//...print the following//

                result.append("No");
            }
            */
            /*
            result.append("\n\nRight eye: ");

//Check whether the right eye is open and print the results//

            if (rightEyeOpenProbability > 0.5) {
                result.append("Open \nProbability: " + rightEyeOpenProbability);
            } else {
                result.append("Close");
            }

            result.append("\n\nLeft eye: ");

//Check whether the left eye is open and print the results//

            if (leftEyeOpenProbability > 0.5) {
                result.append("Open \nProbability: " + leftEyeOpenProbability);
            } else {
                result.append("Close");
            }
            result.append("\n\n");
            */
        }
        return result.toString();
    }




}
