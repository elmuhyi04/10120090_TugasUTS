package view;
// NIM      : 10120090
// Nama     : Muhammad Rizky Muhyi
// Kelas    : IF-3


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import presenter.NoteFragment;
import presenter.InfoAplFragment;
import presenter.ProfilFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 3;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfilFragment();
            case 1:
                return new NoteFragment();
            case 2:
                return new InfoAplFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}