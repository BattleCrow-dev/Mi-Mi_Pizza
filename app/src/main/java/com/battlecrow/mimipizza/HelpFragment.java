package com.battlecrow.mimipizza;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battlecrow.mimipizza.databinding.FragmentHelpBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends Fragment {

    private FragmentHelpBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);

        ChatFragmentCommon chatCommon = new ChatFragmentCommon();
        ChatsFragmentAdmin chatsAdmin = new ChatsFragmentAdmin();

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), getLifecycle());
        pagerAdapter.addFragment(new AboutAuthorFragment(), "Об авторе");

        if (CurrentUserData.isAdmin)
            pagerAdapter.addFragment(chatsAdmin, "Чат");
        else
            pagerAdapter.addFragment(chatCommon, "Чат");

        pagerAdapter.addFragment(new AboutAppFragment(), "О программе");

        ChatsUpdater updater = new ChatsUpdater(chatCommon, chatsAdmin);
        IntentFilter filter = new IntentFilter("com.battlecrow.UPDATE_CHATS");

        requireActivity().registerReceiver(updater, filter, Context.RECEIVER_NOT_EXPORTED);

        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setSaveEnabled(false);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(pagerAdapter.getTitle(position))).attach();
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.dark_green));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.viewPager.setCurrentItem(1, false);
    }

    static class PagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();

        public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }

        public CharSequence getTitle(int position) {
            return titleList.get(position);
        }
    }
}
