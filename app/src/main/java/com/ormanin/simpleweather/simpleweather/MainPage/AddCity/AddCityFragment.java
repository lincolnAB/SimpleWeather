package com.ormanin.simpleweather.simpleweather.MainPage.AddCity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ormanin.simpleweather.simpleweather.Model.SuggestionsModel.SuggestionModel;
import com.ormanin.simpleweather.simpleweather.Model.SuggestionsModel.Prediction;
import com.ormanin.simpleweather.simpleweather.Model.Networking.PlacesService;
import com.ormanin.simpleweather.simpleweather.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AddCityFragment extends Fragment implements AddCityContract.View {

    AddCityPresenter mPresenter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private AddCityRecyclerViewAdapter mAdapter;
    private RecyclerView mRecycler;
    private TextView mNoDataTextView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddCityFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AddCityFragment newInstance(int columnCount, PlacesService service) {
        AddCityFragment fragment = new AddCityFragment();
        fragment.mPresenter = new AddCityPresenter(fragment, service);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addcity_list, container, false);

        mRecycler = view.findViewById(R.id.list);
        mNoDataTextView = view.findViewById(R.id.text_view_no_data);
        TextView textView = view.findViewById(R.id.text_view_search);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.getCitiesSuggestions(charSequence.toString(), new Callback<SuggestionModel>() {
                    @Override
                    public void onResponse(Call<SuggestionModel> call, Response<SuggestionModel> response) {
                        refreshRecycler(response.body());
                    }

                    @Override
                    public void onFailure(Call<SuggestionModel> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Set the adapter
        if (mRecycler != null) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                mRecycler.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecycler.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //mAdapter = new AddCityRecyclerViewAdapter(DummyContent.ITEMS, mListener);
            //mRecycler.setAdapter(mAdapter);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(AddCityContract.Presenter presenter) {
        mPresenter = (AddCityPresenter) presenter;
    }

    @Override
    public void refreshRecycler(SuggestionModel data) {
        if (data == null) return;

        if (data.getPredictions().size() == 0) mNoDataTextView.setVisibility(View.VISIBLE);
        else mNoDataTextView.setVisibility(View.INVISIBLE              );

        mAdapter = new AddCityRecyclerViewAdapter(data.getPredictions(), mListener);
        mRecycler.setAdapter(mAdapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Prediction item);
    }
}
