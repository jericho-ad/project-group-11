package ca.mcgill.ecse321.townlibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.ListFragment;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ca.mcgill.ecse321.townlibrary.databinding.FragmentArchiveBinding;
import cz.msebera.android.httpclient.Header;

/**
 * Code for the archive fragment, corresponds to layout fragment_archive.xml.
 */
public class ArchiveFragment extends ListFragment {

    private FragmentArchiveBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArchiveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> items = new ArrayList<>();

        HttpUtils.get("/archives", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] Header, JSONArray response) {
                try {
                    for (int i=0; i<response.length(); i++) {
                        int itemId = response.getJSONObject(i).getInt("id");
                        String itemName = response.getJSONObject(i).getString("name");
                        items.add(itemId + " - " + itemName);

                        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
                        ListView listView = (ListView) binding.list;
                        listView.setAdapter(itemsAdapter);
                    }
                } catch (JSONException e) {
                    // ignored plz
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
