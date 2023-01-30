package com.example.eulerity_hackathon.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.example.eulerity_hackathon.Models.Image;
import com.example.eulerity_hackathon.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class imagesAdapter extends ArrayAdapter<Image> {
    public imagesAdapter(@NonNull Context context, List<Image> images) {
        super(context, 0, images);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.images_list, null, true);
        }

        Image image = getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imageViewProduct);
        Picasso.get().load(image.getUrl()).into(imageView);

        return convertView;
    }
}
