package com.pixel.number;

import android.app.Service;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private final int[] btnNUMid = {
            R.id.buttonZero, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8,
            R.id.button9, R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE,
            R.id.buttonF};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Button btnNum[] = new Button[btnNUMid.length];
    private EditText dec;
    private EditText hex;
    private EditText bin;
    private ImageButton bkspBtn;
    private Vibrator mVibrator;
    private long number;
    private long[] paShort = {0, 10, 60, 5};
    private long[] paLong = {0, 10, 100, 30};
    private boolean loadingResult = true;

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

        View view = inflater.inflate(R.layout.fragment_conv, container, false);
        dec = view.findViewById(R.id.toDec);
        hex = view.findViewById(R.id.toHex);
        bin = view.findViewById(R.id.toBin);
        dec.setInputType(InputType.TYPE_NULL);
        hex.setInputType(InputType.TYPE_NULL);
        bin.setInputType(InputType.TYPE_NULL);
        bkspBtn = view.findViewById(R.id.bkspButton);
        bkspBtn.setEnabled(false);
        mVibrator = (Vibrator) getActivity().getApplication().getSystemService(Service.VIBRATOR_SERVICE);

        bkspBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dec.hasFocus()) backSpace(dec);
                if (hex.hasFocus()) backSpace(hex);
                if (bin.hasFocus()) backSpace(bin);
            }

            private void backSpace(EditText editText) {
                if (editText.length() > 0) {
                    editText.setText(editText.getText().toString().substring(0, editText.length() - 1));
                    mVibrator.vibrate(20);
                }
            }
        });

        bkspBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mVibrator.vibrate(paLong, -1);
                dec.setText("");
                hex.setText("");
                bin.setText("");
                return false;
            }
        });

        dec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    btnNum[2].setEnabled(true);
                    btnNum[3].setEnabled(true);
                    btnNum[4].setEnabled(true);
                    btnNum[5].setEnabled(true);
                    btnNum[6].setEnabled(true);
                    btnNum[7].setEnabled(true);
                    btnNum[8].setEnabled(true);
                    btnNum[9].setEnabled(true);
                    btnNum[10].setEnabled(false);
                    btnNum[11].setEnabled(false);
                    btnNum[12].setEnabled(false);
                    btnNum[13].setEnabled(false);
                    btnNum[14].setEnabled(false);
                    btnNum[15].setEnabled(false);
                }
            }
        });

        dec.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (dec.hasFocus() || loadingResult) {
                    if (dec.getText().toString().equals("")) {
                        hex.setText("");
                        bin.setText("");
                        bkspBtn.setEnabled(false);
                    } else {
                        try {
                            number = Long.parseLong(dec.getText().toString());
                            hex.setText(Long.toHexString(number).toUpperCase());
                            bin.setText(Long.toBinaryString(number));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Number is too large(overflow)", Toast.LENGTH_SHORT).show();
                            hex.setText("");
                            bin.setText("");
                        }
                        bkspBtn.setEnabled(true);
                        switch (dec.getText().toString()) {
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
            }
        });

        hex.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                for (Button aBtnNum : btnNum) {
                    aBtnNum.setEnabled(true);
                }
            }
        });
        hex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (hex.hasFocus()) {
                    if (hex.getText().toString().equals("")) {
                        dec.setText("");
                        bin.setText("");
                        bkspBtn.setEnabled(false);
                    } else {
                        try {
                            number = Long.valueOf(hex.getText().toString(), 16);
                            dec.setText(Long.toString(number));
                            bin.setText(Long.toBinaryString(number));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Wrong input(hex)", Toast.LENGTH_SHORT).show();
                            dec.setText("");
                            bin.setText("");
                        }
                        bkspBtn.setEnabled(true);
                    }
                }

            }
        });

        bin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                for (int i = 2; i <= 15; i++) {
                    btnNum[i].setEnabled(false);
                }
            }
        });

        bin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (bin.hasFocus()) {
                    if (bin.getText().toString().equals("")) {
                        dec.setText("");
                        hex.setText("");
                        bkspBtn.setEnabled(false);
                    } else {
                        try {
                            number = Long.valueOf(bin.getText().toString(), 2);
                            dec.setText(Long.toString(number));
                            hex.setText(Long.toHexString(number).toUpperCase());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Wrong input(binary)", Toast.LENGTH_SHORT).show();
                            hex.setText("");
                            dec.setText("");
                        }
                        bkspBtn.setEnabled(true);
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
        SharedPreferences last = getActivity().getSharedPreferences("last", MODE_PRIVATE);
        dec.setText(last.getString("LastResult", ""));
        loadingResult = false;
        if (!last.contains("LastResult")) {
            Toast.makeText(getContext(), "There is no last result.", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("last", MODE_PRIVATE).edit();
        editor.putString("LastResult", dec.getText().toString());
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
            if (dec.hasFocus()) dec.append(digit);
            if (hex.hasFocus()) hex.append(digit);
            if (bin.hasFocus()) bin.append(digit);
        }
    }
}
