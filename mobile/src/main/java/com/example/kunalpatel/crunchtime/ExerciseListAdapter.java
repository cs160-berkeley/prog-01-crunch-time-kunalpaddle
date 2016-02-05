package com.example.kunalpatel.crunchtime;

import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kunalpatel.crunchtime.R;

/**
 * Created by Kunal Patel on 2/5/2016.
 */
public class ExerciseListAdapter extends ArrayAdapter<Exercise> {

    private final Context context;
    private final ArrayList<Exercise> exercisesArrayList;

    public ExerciseListAdapter(Context context, ArrayList<Exercise> exercisesArrayList) {

        super(context, R.layout.target_item, exercisesArrayList);
        this.context = context;
        this.exercisesArrayList = exercisesArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Make new inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. inflate row view

        View rowView = null;
        rowView = inflater.inflate(R.layout.target_item, parent, false);

        // 3. Get icon,title & counter views from the rowView
        ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
        TextView counterView = (TextView) rowView.findViewById(R.id.item_counter);
        TextView exerciseType = (TextView) rowView.findViewById(R.id.exercise_type);


        // 4. Set icon and count
        Exercise current = exercisesArrayList.get(position);
        exerciseType.setText(current.getMeasurementType());
        String uri = "@drawable/"+current.key;


        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());


        Bitmap bm = downscaleBitmapUsingDensities(2, imageResource);
        imgView.setImageBitmap(bm);
        titleView.setText(current.getName());

        double currentCount = current.getCount();
        String countValue = String.valueOf(currentCount);
        if (current.getMeasurementType().equals(Exercise.TYPE_REPS)) {
            countValue = String.valueOf((int) Math.round(currentCount*1)/1);
        }
        counterView.setText(countValue);

        return rowView;
    }

    /*

        Credit for this downscale function goes to Farhan on stackoverflow!
     */
    private Bitmap downscaleBitmapUsingDensities(final int sampleSize,final int imageResId)
    {
        final BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
        bitmapOptions.inDensity=sampleSize;
        bitmapOptions.inTargetDensity=1;
        final Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(),imageResId,bitmapOptions);
        scaledBitmap.setDensity(Bitmap.DENSITY_NONE);
        return scaledBitmap;
    }


}
