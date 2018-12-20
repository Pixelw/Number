package com.pixel.number;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Conv.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Conv#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Conv extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private final int[] btnNUMid = {
            R.id.buttonZero, R.id.button1, R.id.button1, R.id.button2, R.id.button3, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button6, R.id.button7, R.id.button8,
            R.id.button9};
    private Button btnNum[] = new Button[btnNUMid.length];
    private EditText inputStr;
    private TextView hex;
    private TextView bin;
    private ImageButton bkspBtn;
    private Vibrator mVibrator;
    private long number;
    private long[] paShort = {0, 10, 60, 5};
    private long[] paLong = {0, 10, 100, 30};
    private View view;

    public Conv() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Conv.
     */
    // TODO: Rename and change types and number of parameters
    public static Conv newInstance(String param1, String param2) {
        Conv fragment = new Conv();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_conv,container,false);
        inputStr = view.findViewById(R.id.inputs);
        hex = view.findViewById(R.id.toHex);
        bin = view.findViewById(R.id.toBin);
        bkspBtn = view.findViewById(R.id.bkspButton);
        bkspBtn.setEnabled(false);
        mVibrator = (Vibrator) getActivity().getApplication().getSystemService(Service.VIBRATOR_SERVICE);

        inputStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputStr.setCursorVisible(true);
            }
        });

        bkspBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputStr.length() > 0) {
                    inputStr.setText(inputStr.getText().toString().substring(0, inputStr.length() - 1));//backspace
                    mVibrator.vibrate(20);
                }

            }
        });

        bkspBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mVibrator.vibrate(paLong, -1);
                inputStr.setText("");
                inputStr.setCursorVisible(false);
                return false;
            }
        });

        inputStr.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (inputStr.getText().toString().equals("")) {
                    hex.setText("0");
                    bin.setText("0");
                    bkspBtn.setEnabled(false);
                } else {
                    number = Long.parseLong(inputStr.getText().toString());
                    hex.setText("0x" + Long.toHexString(number).toUpperCase());
                    bin.setText(Long.toBinaryString(number));
                    bkspBtn.setEnabled(true);
                    switch (inputStr.getText().toString()) {
                        case "39":
                            Toast.makeText(getActivity(), "miku!!", Toast.LENGTH_SHORT).show();
                            break;
                        case "20070831":
                            Toast.makeText(getActivity(), "birthday!!", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }

            }
        });
        for (int i = 0; i < btnNUMid.length; i++) {
            btnNum[i] = view.findViewById(btnNUMid[i]);
            btnNum[i].setOnClickListener(new KeyOnClick(btnNum[i].getText().toString()));//get numbers from buttons.
            btnNum[i].setOnTouchListener(new KeyDown());
        }
        loadResult();
        return view;
    }

    private void loadResult() {
        SharedPreferences last = getActivity().getSharedPreferences("last",MODE_PRIVATE);
        inputStr.setText(last.getString("LastResult", ""));
        if (!last.contains("LastResult")) {
            Toast.makeText(getContext(), "There is no last result.", Toast.LENGTH_SHORT).show();
        }
    }
    class KeyDown implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mVibrator.vibrate(paShort, -1);
            }
            return false;
        }
    }

    class KeyOnClick implements View.OnClickListener {
        String digit;

        private KeyOnClick(String msg) {
            digit = msg;
        }

        @Override
        public void onClick(View v) {
            //mVibrator.vibrate(10);
            inputStr.append(digit);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /*
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("last", MODE_PRIVATE).edit();
        editor.putString("LastResult", inputStr.getText().toString());
        editor.apply();
        Toast.makeText(getContext(), "Result Saved", Toast.LENGTH_SHORT).show();
        mVibrator.cancel();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
