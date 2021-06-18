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
    private final boolean[] hidden;

    public int getSelected() {
        return selected;
    }

    int selected = -1;

    // 1
    public InputAdapter(Context context, String[] text) {
        this.mContext = context;
        this.characters = text;
        this.hidden = new boolean[text.length];
    }

    public void setHidden(int position, boolean isUsed){
        hidden[position] = isUsed;
    }

    public void setSelected(int selected) {
        this.selected = selected;
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
        final TextView button2 = convertView.findViewById(R.id.input_button2);
        final TextView button3 = convertView.findViewById(R.id.input_button3);

        // 4
        button.setText(character);
        button2.setText(character);


        button.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);

        if (hidden[position]) {
            if (position < 7 && characters[position] != null)
                button3.setVisibility(View.VISIBLE);
        }
        else if (position == selected)
            button2.setVisibility(View.VISIBLE);
        else
            button.setVisibility(View.VISIBLE);

        return convertView;
    }

}
