package presenter;
// NIM      : 10120090
// Nama     : Muhammad Rizky Muhyi
// Kelas    : IF-3


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.tugasuts_10120090.R;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        return view;
    }
}
