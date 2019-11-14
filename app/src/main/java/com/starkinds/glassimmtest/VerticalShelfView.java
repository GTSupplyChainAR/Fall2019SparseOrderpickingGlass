package com.starkinds.glassimmtest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class VerticalShelfView extends LinearLayout {
    private static final String TAG = "|VerticalShelfView|";

    private Context context;
    private TextView columnTag, author_view, title_view;
    private ImageView[] shelves;

    private int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private ArrayList<Book> bookList = new ArrayList<Book>();
    private Book book0 = new Book("The Complete Persepolis",3,5);
    private Book book1 = new Book("Practical Digital ...",2,3);
    private Book book2 = new Book("Public Enemies",3,5);
    private Book book3 = new Book("Cognitive Science",3,4);
    private Book book4 = new Book("Pegasus in Space",3,1);
    private Book book5 = new Book("Rise & Resurrection ...", 1, 1);
    private Book book6 = new Book("Discrete-Time Signal...", 3,1);
    private Book book7 = new Book("Statistics for Experimenters", 3,2);
    private Book book8 = new Book("Conqueror’s Legacy", 2,5);
    private Book book9 = new Book("Selected Reprints On ...", 1, 2);


    // CONSTRUCTOR
    public VerticalShelfView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void init(){
        bookList.add(book0);
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
        bookList.add(book6);
        bookList.add(book7);
        bookList.add(book8);
        bookList.add(book9);
        this.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        this.setOrientation(HORIZONTAL);

        LinearLayout book_info_layout = new LinearLayout(context);
        book_info_layout.setLayoutParams(new LayoutParams(0, MP, 0.7f));
        book_info_layout.setOrientation(VERTICAL);
        book_info_layout.setGravity(Gravity.CENTER);
        int padding_px = 30;
        book_info_layout.setPadding(padding_px, padding_px, padding_px, padding_px);

        author_view = new TextView(context);
        author_view.setLayoutParams(new LayoutParams(WC, WC));
        applyTextStyle(author_view);
        author_view.setText(bookList.get(0).getTitle());
        book_info_layout.addView(author_view);

        title_view = new TextView(context);
        title_view.setLayoutParams(new LayoutParams(WC, WC));
        applyTextStyle(title_view);
        String col = Integer.toString(bookList.get(0).getCol());
        col = "Col: " + col;
        title_view.setText(col);
        book_info_layout.addView(title_view);

        LinearLayout vertical_rack_layout = new LinearLayout(context);
        vertical_rack_layout.setLayoutParams(new LayoutParams(0, MP, 0.3f));
        vertical_rack_layout.setOrientation(VERTICAL);
        vertical_rack_layout.setPadding(padding_px, padding_px, padding_px, padding_px);

        columnTag = new TextView(context);
        LayoutParams lp = new LayoutParams(WC, WC);
        lp.gravity = Gravity.CENTER;
        columnTag.setLayoutParams(lp);
        applyTextStyle(columnTag);
        columnTag.setText("1");
        vertical_rack_layout.addView(columnTag);


        int n = Prefs.VERTICAL_HEIGHT;
        shelves = new ImageView[n];
        for(int i = 0; i < Prefs.VERTICAL_HEIGHT ; i++){
            ImageView shelf = new ImageView(context);
            LayoutParams lpi = new LayoutParams(MP,0, 1f);
            int margins = 15;
            lpi.setMargins(margins, margins, margins, margins);
            shelf.setLayoutParams(lpi);
            if(i==bookList.get(0).getRow()-1){
                shelf.setBackgroundColor(Color.GREEN);
            }
            else
                shelf.setBackgroundColor(Color.RED);
            shelves[i] = shelf;
            vertical_rack_layout.addView(shelf);
        }


        this.addView(book_info_layout);
        this.addView(vertical_rack_layout);
    }

    private void applyTextStyle(TextView textView){
        textView.setTextColor(Color.rgb(255,255,255));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
    }

    public void setTargetBook(Book newBook){

        author_view.setText("Author: " + newBook.getAuthor().toUpperCase());
        title_view.setText("Title: "+newBook.getTitle().toUpperCase());
        String tag = newBook.getLocationTag();
        Log.d(TAG, tag);
        char vertTag = tag.charAt(tag.length()-1);
        int vertIndex = 0;
        switch (vertTag){
            case 'A': vertIndex = 0; break;
            case 'B': vertIndex = 1; break;
            case 'C': vertIndex = 2; break;
            case 'D': vertIndex = 3; break;
            case 'E': vertIndex = 4; break;
            case 'F': vertIndex = 5; break;
        }
        for(ImageView shelf : shelves)
            shelf.setBackgroundColor(Color.RED);
        shelves[vertIndex].setBackgroundColor(Color.GREEN);

        int rackIndex = Integer.parseInt(tag.charAt(tag.length() - 4)+""
                                                +tag.charAt(tag.length()-3));
        if(rackIndex%2==1) {
            rackIndex--;
            rackIndex = 10-rackIndex;
        }
        rackIndex /= 2;
        rackIndex ++;
        columnTag.setText(rackIndex+"");
        //title_view.setText(tag);
    }

    public void nextBook(int currentBook){
        author_view.setText(bookList.get(currentBook).getTitle());
        String col = Integer.toString(bookList.get(currentBook).getCol());
        col = "Col: " + col;
        title_view.setText(col);
        int location = bookList.get(currentBook).getRow();
        columnTag.setText(Integer.toString(location));
        for(ImageView shelf : shelves)
            shelf.setBackgroundColor(Color.RED);
        shelves[location-1].setBackgroundColor(Color.GREEN);
    }
}
