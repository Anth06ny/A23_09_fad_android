package com.amonteiro.a23_09_fad_android.exemplefragment;

import android.os.Bundle;

import com.amonteiro.a23_09_fad_android.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amonteiro.a23_09_fad_android.databinding.ActivityExempleFragmentBinding;

public class ExempleFragmentActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    private ActivityExempleFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExempleFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);
//

//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(ExempleFragmentActivity.this, R.id.nav_host_fragment_content_exemple_fragment);

                //Changement de fragment depuis une activity
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_exemple_fragment);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}