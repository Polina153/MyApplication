package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.observe.Publisher;
import com.example.myapplication.ui.Navigation;
import com.example.myapplication.ui.SocialNetworkFragment;

public class MainActivity extends AppCompatActivity {

    //Pushistik wants to hug!!!
    //Really wants
    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        initToolbar();
        //addFragment(SocialNetworkFragment.newInstance());
        getNavigation().addFragment(SocialNetworkFragment.newInstance(), false);
    }

    private void initToolbar() {
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    /*    private void addFragment(Fragment fragment) {
            //Получить менеджер фрагментов
            FragmentManager fragmentManager = getSupportFragmentManager();
            // Открыть транзакцию
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            // Закрыть транзакцию
            fragmentTransaction.commit();
        }*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}
