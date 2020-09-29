//package com.flagcamp.secondhands.ui.post;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.flagcamp.secondhands.R;
//
//
//public class PostFragment extends Fragment {
//
//
//    public PostFragment() {
//        // Required empty public constructor
//    }
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_post, container, false);
//    }
//}

package com.flagcamp.secondhands.ui.post;;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.flagcamp.secondhands.MainActivity;
import com.flagcamp.secondhands.CurrentUserSingleton;
import com.flagcamp.secondhands.MainActivity;
import com.flagcamp.secondhands.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.flagcamp.secondhands.databinding.FragmentPostBinding;
import com.flagcamp.secondhands.model.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;



public class PostFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage; // Choose one product image;
    private Button mButtonPostProduct; // Post product;
    private TextView mTextViewShowProductImages; //show All product images;

    private EditText mProductTitle; // Product Title;
    private EditText mProductDescription; // Product Description;
    private EditText mProductPrice; // Product Price;
    private EditText mProductCategory; // Product Category;
    private EditText mProductLocationState; // User Location: CA
    private ImageView mImageView; // Product image;
    private ProgressBar mProgressBar;
    private Uri mImageUri; // Product image URL;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask uploadTask;

    private FragmentPostBinding binding;

    private Product product;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Change fragment Action Bar Title, we can delete it if Action Bar is invisiable;
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Post Product");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        binding = FragmentPostBinding.inflate(inflater, container, false);
//        return binding.getRoot();

        View view =  inflater.inflate(R.layout.fragment_post, container, false);

       // setContentView(R.layout.fragment_post);
        mButtonChooseImage = view.findViewById(R.id.button_chooseProductImage);
        mButtonPostProduct = view.findViewById(R.id.button_postProduct);

        mProductTitle = view.findViewById(R.id.product_title);
        mProductDescription = view.findViewById(R.id.product_description);
        mProductPrice = view.findViewById(R.id.product_price);

        setUpCategory();
        setUpLocation();

        mTextViewShowProductImages = view.findViewById(R.id.text_view_showProductImages);

        mImageView = view.findViewById(R.id.image_view);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("products");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("products");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        mButtonPostProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "Post in progress", Toast.LENGTH_SHORT).show();
                } else {
                    postProduct();
                }
            }
        });
        mTextViewShowProductImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void postProduct() {
        if (mImageUri != null) { //TOD; check if title is null;
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            uploadTask = fileReference.putFile(mImageUri)

//            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//
//                    // Continue with the task to get the download URL
//                    return fileReference.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                         mImageUri = task.getResult();
//                    } else {
//                        // Handle failures
//
//                    }
//                }
//            });

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);


//                            Product(String seller, String description, String postedAt, String title, String price,
//                            String urlToImageFolder, String urlToImage, String id, boolean favorite)

                            String productId = mDatabaseRef.push().getKey();

                            product = new Product(CurrentUserSingleton.getInstance().getUserId(),
                                                            productId,
                                                            mProductTitle.getText().toString().trim(),
                                                            mProductPrice.getText().toString(),
                                                            mProductDescription.getText().toString(),
                                                            fileReference.getDownloadUrl().toString());

                            mDatabaseRef.child(productId).setValue(product);

                            Toast.makeText(getContext(), "Post successful", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show(); //please complete input info!
        }
    }

    private void setUpCategory() {
        ArrayAdapter<CharSequence> categorySpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.productCategorySpinner.setAdapter(categorySpinnerAdapter);
        binding.productCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product.setCategory(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpLocation() {
        ArrayAdapter<CharSequence> locationSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.location, android.R.layout.simple_spinner_item);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.productLocationSpinner.setAdapter(locationSpinnerAdapter);
        binding.productLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product.setLocation(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
