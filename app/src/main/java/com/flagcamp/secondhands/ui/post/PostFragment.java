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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;

import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.flagcamp.secondhands.databinding.FragmentPostBinding;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRepository;
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

import java.util.Date;

public class PostFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri; // Product image URL;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask uploadTask;
    private StorageReference mProductRef;
    private PostViewModel viewModel;

    private FragmentPostBinding binding;
    private Product product;
    private String category;
    private String state;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpCategory();
        setUpLocation();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mProductRef = mStorageRef.child("products");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("products");
        binding.buttonChooseProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        binding.buttonPostProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "Post in progress", Toast.LENGTH_SHORT).show();
                } else {
                    postProduct();
                }
            }
        });
        binding.textViewShowProductImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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
            Picasso.get().load(mImageUri).into(binding.imageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void postProduct() {
        String productId = mDatabaseRef.push().getKey();
        product = new Product(productId);

        if (mImageUri == null) {
            Toast.makeText(getContext(), "Please upload your product image", Toast.LENGTH_SHORT).show(); //please complete input info!
        } else if (binding.productTitle.getText().toString().matches("")) {
            Toast.makeText(getContext(), "Please enter your product title", Toast.LENGTH_SHORT).show();
        } else if (binding.productPrice.getText().toString().matches("")) {
            Toast.makeText(getContext(), "Please enter your product price", Toast.LENGTH_SHORT).show();
        } else {
            StorageReference fileReference = mProductRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            uploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.progressBar.setProgress(0);
                                }
                            }, 500);

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
                            binding.progressBar.setProgress((int) progress);
                        }
                    });

            fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        product.setUrlToImageFolder(task.getResult().toString());
                    }
                }
            });

            product.setTitle(binding.productTitle.getText().toString());
            product.setDescription(binding.productDescription.getText().toString());
            product.setPrice(binding.productPrice.getText().toString());

            // TODO: get device lon and lat

            product.setCategory(category);
            product.setLocation(state);
            product.setPostedAt(new Date().getTime());

            mDatabaseRef.child(productId).setValue(product); // For test

            // TODO: post to back-end db
            ProductRepository repository = new ProductRepository(requireContext());
            viewModel.postProduct(product);
        }
    }


    private void setUpCategory() {
        ArrayAdapter<CharSequence> categorySpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.productCategorySpinner.setAdapter(categorySpinnerAdapter);
        binding.productCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
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
                state = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
