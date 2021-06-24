package dtu.gruppe04.littlebrain.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import dtu.gruppe04.littlebrain.R;


// Group nr. 4

public class InputAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] characters;
    private final boolean[] pile;

    public int getSelected() {
        return selected;
    }

    int selected = -1;
    int highlighted = -1;
    int suggested = -1;

    public void setSuggested(int suggested) {
        this.suggested = suggested;
    }

    public int getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(int highlighted) {
        this.highlighted = highlighted;
    }

    // 1
    public InputAdapter(Context context, String[] text) {
        this.mContext = context;
        this.characters = text;
        this.pile = new boolean[text.length];
    }

    public void setPile(int position, boolean isUsed){
        pile[position] = isUsed;
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

        // 4
        button.setText(character);

        Resources res = mContext.getResources();

        if (characters[position] == null)
            button.setVisibility(View.GONE);
        else
            button.setVisibility(View.VISIBLE);

        if (pile[position]) {
            if (position == suggested)
                button.setBackground(ResourcesCompat.getDrawable(res, R.drawable.selected_pile, null));
            else
                button.setBackground(ResourcesCompat.getDrawable(res, R.drawable.empty_pile, null));
        }
        else if (position == highlighted)
            button.setBackground(ResourcesCompat.getDrawable(res, R.drawable.higlighted_card, null));
        else if (position == suggested)
            button.setBackground(ResourcesCompat.getDrawable(res, R.drawable.best_move, null));
        else if (position == selected)
            button.setBackground(ResourcesCompat.getDrawable(res, R.drawable.selected_card, null));
        else if ("".equals(character))
            button.setBackground(ResourcesCompat.getDrawable(res, R.drawable.card_back, null));
        else
            button.setBackground(ResourcesCompat.getDrawable(res, R.drawable.rounded_corner, null));

        return convertView;
    }

}
