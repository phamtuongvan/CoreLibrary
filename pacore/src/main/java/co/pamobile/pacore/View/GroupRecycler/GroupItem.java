package co.pamobile.pacore.View.GroupRecycler;

import java.util.ArrayList;

/**
 * Created by Dev02 on 4/17/2017.
 */

public class GroupItem {
    public String headerTitle;
    public ArrayList<Object> listSingleItem;

    public GroupItem() {

    }

    public GroupItem(String headerTitle, ArrayList<Object> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.listSingleItem = allItemsInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Object> getListSingleItem() {
        return listSingleItem;
    }

    public void setListSingleItem(ArrayList<Object> listSingleItem) {
        this.listSingleItem = listSingleItem;
    }
}
