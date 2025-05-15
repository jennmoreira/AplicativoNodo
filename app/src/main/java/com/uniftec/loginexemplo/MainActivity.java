package com.uniftec.loginexemplo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.VPAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabItem;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tabItem = findViewById(R.id.tabItem);
        viewPager = findViewById(R.id.viewpager);

        tabItem.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new FragmentEventos(),"Eventos");
        vpAdapter.addFragment(new FragmentPrestadores(),"Candidatos");
        viewPager.setAdapter(vpAdapter);
    }
}