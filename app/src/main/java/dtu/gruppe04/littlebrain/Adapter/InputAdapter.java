package dtu.gruppe04.littlebrain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import dtu.gruppe04.littlebrain.R;


public class InputAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] characters;
    private final boolean[] used;

    // 1
    public InputAdapter(Context context, String[] characters) {
        this.mContext = context;
        this.characters = characters;
        this.used = new boolean[characters.length];
    }

    public void setUsed(int position, boolean isUsed){
        used[position] = isUsed;
    }

    // 2
    @Override
    public int getCount() {
        return characters.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return characters[position];
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final String character = characters[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_input, null);
        }

        // 3
        final TextView button = convertView.findViewById(R.id.input_button);

        // 4
        button.setText(character);

        if (used[position]) {
            button.setVisibility(View.GONE);
        }
        else {
            button.setVisibility(View.VISIBLE);
        }

        if (position == 0)
            convertView.findViewById(R.id.addedHeight).setVisibility(View.VISIBLE);

        return convertView;
    }

}
