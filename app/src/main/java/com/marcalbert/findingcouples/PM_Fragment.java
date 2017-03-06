package com.marcalbert.findingcouples;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Random;

/**
 * Created by Marc Albert Piñeiro on 04/03/2017.
 */
public class PM_Fragment extends Fragment implements AdapterView.OnItemSelectedListener{

    View rootView;
    GridView gridView;
    TextView text, winner_text;
    Spinner spinner;
    Button resetButton;
    int counter, imageSelected, volt, gan;
    public Integer[] mThumbIds;

    int[] num;
    int[] cot;
    int[] aux = new int[2];
    boolean[] est;
    ImageView imgView0, imgView1;

    //work combinations
    int totalGridColumn = 4;
    int[] numGridColumn     = new int[] { 0,0,2,0,4,0,6 };
    int[] couplesGridColumn = new int[] { 0,0,2,0,8,0,18 };
    int[] cellGridColumn    = new int[] { 0,0,4,0,16,0,36 };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //set initial data
        initActivity();

        //set gridView adapter with default images
        gridView.setAdapter(new ImageAdapter(getActivity(), cellGridColumn[totalGridColumn]));

        //change image in GridView when clicked event
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View imgView, int position, long id) {

                //check if the selected image is different
                if( imageSelected != position && !est[position] && aux[0] != position){
                    ImageView imageView = (ImageView) imgView;
                    imageView.setImageResource(mThumbIds[position]);

                    if(volt == 0){
                        imgView0 = (ImageView) imgView;
                    }else if(volt == 1){
                        imgView1 = (ImageView) imgView;
                        checkFinishGame();
                    }else
                    if (volt==2) {
                        checkImages();
                        imgView0 = (ImageView) imgView;
                    }
                    volt++;
                    aux[volt-1]=position;

                    //set image selected
                    imageSelected = position;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        rootView = inflater.inflate(R.layout.pm_fragment, container, false);

        //set textView
        text= (TextView) rootView.findViewById(R.id.counter_text_view);
        winner_text= (TextView) rootView.findViewById(R.id.winner_text_view);

        //set spinner
        spinner = (Spinner) rootView.findViewById(R.id.spinner1);

        //set default matrix to 4 NxN
        spinner.setSelection(1);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        //set gridView
        gridView = (GridView) rootView.findViewById(R.id.grid_view);

        //set reset button
        resetButton = (Button) rootView.findViewById(R.id.button1);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                resetGridView();
            }
        });

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        //set grid view matrix
        totalGridColumn = Integer.parseInt(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //initialize variables and define grid view
    private void initActivity(){
        //initialize variables
        counter = 0;
        imageSelected = -1;
        gan = 0;
        volt = 0;
        aux[0] = -1;
        aux[0] = -1;
        setCounter(false);
        winner_text.setText("");

        //create grid with a random images
        setGridRandomImages(totalGridColumn);
        gridView.setNumColumns(totalGridColumn);

        //set gridView adapter with default images
        gridView.setAdapter(new ImageAdapter(getActivity(), cellGridColumn[totalGridColumn]));
    }

    //define random images for the grid view
    private void setGridRandomImages(int numGridColumn){

        int total = cellGridColumn[totalGridColumn];
        num = new int[couplesGridColumn[totalGridColumn]];
        cot = new int[total];
        est = new boolean[total];
        int img;
        mThumbIds = new Integer[total];

        Random r = new Random();
        for(int cant=0;cant<total;cant++){
            img = r.nextInt(couplesGridColumn[totalGridColumn]);
            if (num[img]<2){
                num[img]++;
                cot[cant]=img+1;
                mThumbIds[cant] = rootView.getContext().getResources().getIdentifier("pic_"+img, "drawable", "com.marcalbert.findingcouples");
            }else{
                cant--;
            }
        }
    }

    //check if the game has been finished
    private void checkFinishGame(){
        if (gan == num.length - 1){
            setCounter(true);
        }
    }

    //check if selected images are the same
    private void checkImages(){
        //both images are equal.
        if(cot[aux[0]]==cot[aux[1]]){
            est[aux[0]]=true;
            est[aux[1]]=true;
            volt=0;
            gan++;

        }else{
            volt=0;

            //set these two images with a init image
            int resId = rootView.getContext().getResources().getIdentifier("pic_init", "drawable", "com.marcalbert.findingcouples");

            ImageView imageView0 = (ImageView) imgView0;
            imageView0.setImageResource(resId);

            ImageView imageView1 = (ImageView) imgView1;
            imageView1.setImageResource(resId);
        }

        //increment counter
        setCounter(false);
    }

    //reset button implementation
    private void resetGridView(){
        initActivity();
        gridView.invalidateViews();
    }

    //show step numbers and finish game
    private void setCounter(boolean showWinnerStatus){
        String message = rootView.getContext().getString(R.string.counter) + ": " + counter++;

        if(showWinnerStatus) {
            //message += "  " + rootView.getContext().getString(R.string.winner);
            winner_text.setText(rootView.getContext().getString(R.string.winner));
        }

        text.setText(message);
    }
}
