package com.codeinfinity.oipp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    FragmentManager fragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





       // FragmentManager fragmentManager = getSupportFragmentManager();

        // Load HomeFragment by default
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, homeFragment);


        transaction.commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeBottomMenu) {
                    replaceFragment(new HomeFragment());
                    return true;

                    // Deselect other items if needed
//                    bottomNavigationView.getMenu().findItem(R.id.navigation_saved).setIcon(R.drawable.save_menu);
//                    bottomNavigationView.getMenu().findItem(R.id.navigation_profile).setIcon(R.drawable.user_menu);

                } else if (item.getItemId() == R.id.communityBottomMEnu) {
//                    // Handle Saved item selection
//                    item.setIcon(R.drawable.save_select);
                    replaceFragment(new CommunityFragment());
                    return true;
//                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//
//
//                    SaveFragment savedFragment = new SaveFragment();
//                    FragmentTransaction saveTransaction = fragmentManager.beginTransaction();
//                    saveTransaction.replace(R.id.mainFrameLayout, savedFragment);
//                    saveTransaction.commit();
//                    // Deselect other items if needed
//                    bottomNavigationView.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.home_menu);
//                    bottomNavigationView.getMenu().findItem(R.id.navigation_profile).setIcon(R.drawable.user_menu);
//                    return true;
                } else if (item.getItemId() == R.id.profileBottomMenu) {

                    replaceFragment(new ProfileFragment());
                    return true;
//                    // Handle Profile item selection
//                    item.setIcon(R.drawable.user_select);
//
//                    ProfileFragment profileFragment = new ProfileFragment();
//                    FragmentTransaction profileTransaction = fragmentManager.beginTransaction();
//                    profileTransaction.replace(R.id.mainFrameLayout, profileFragment);
//                    profileTransaction.commit();
//                    // Deselect other items if needed
//                    bottomNavigationView.getMenu().findItem(R.id.navigation_home).setIcon(R.drawable.home_menu);
//                    bottomNavigationView.getMenu().findItem(R.id.navigation_saved).setIcon(R.drawable.save_menu);
//                    return true;
                }else if (item.getItemId() == R.id.chatBottomMenu) {

                    replaceFragment(new ChatsFragment());
                    return true;
                }
                return false;
            }
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment);
        transaction.commit();
    }


}