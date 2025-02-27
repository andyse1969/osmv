package at.the.gogo.parkoid.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import at.the.gogo.parkoid.R;
import at.the.gogo.parkoid.models.Car;
import at.the.gogo.parkoid.util.CoreInfoHolder;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class CarListFragment extends SherlockListFragment implements
        PageChangeNotifyer {

    private int     mPositionChecked = 0;
    private int     mPositionShown   = -1;
    private Car     carSelected;
    private boolean isInitialized    = false;

    TextView        nrOfEntries;

    public static CarListFragment newInstance() {
        final CarListFragment f = new CarListFragment();

        return f;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {

        restoreSavedState(savedInstanceState);
        final View view = inflater.inflate(R.layout.headerlist, null);

        final ImageView icon = (ImageView) view.findViewById(R.id.parkButton);
        final TextView header = (TextView) view
                .findViewById(R.id.currentAddress);

        icon.setImageResource(R.drawable.car);
        icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                getActivity().openOptionsMenu();
            }
        });

        header.setText(R.string.page_title_car);

        nrOfEntries = (TextView) view.findViewById(R.id.locationCaption);
        nrOfEntries.setText(R.string.nr_of_entries);

        header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                getActivity().openOptionsMenu();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.car_option_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void restoreSavedState(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPositionChecked = savedInstanceState.getInt("curChoiceList", 0);
            mPositionShown = savedInstanceState.getInt("shownChoiceList", -1);
        }
    }

    @Override
    public void onListItemClick(final ListView l, final View v,
            final int position, final long id) {
        v.showContextMenu();
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v,
            final ContextMenuInfo menuInfo) {

        final int id = (int) ((AdapterView.AdapterContextMenuInfo) menuInfo).id;
        carSelected = CoreInfoHolder.getInstance().getDbManager().getCar(id);

        menu.setHeaderTitle(carSelected.getName() + " ("
                + carSelected.getLicence() + ")");

        menu.add(0, R.id.menu_editcar, 0, getText(R.string.menu_editcar));
        menu.add(0, R.id.menu_deletecar, 0, getText(R.string.menu_deletecar));

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addcar: {
                addCar();
                break;
            }
            case R.id.deleteallcars: {
                deleteAllCars();
                break;
            }
        }

        return true;
    }

    @Override
    public boolean onContextItemSelected(final android.view.MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.menu_editcar: {
                editCar(carSelected.getId());
                result = true;
                break;
            }
            case R.id.menu_deletecar: {
                deleteCar(carSelected.getId());
                result = true;
                break;
            }
        }
        return result;
    }

    // @Override
    // public boolean onContextItemSelected(final MenuItem item) {
    //
    // boolean result = false;
    // switch (item.getItemId()) {
    // case R.id.menu_editcar: {
    // editCar(carSelected.getId());
    // result = true;
    // break;
    // }
    // case R.id.menu_deletecar: {
    // deleteCar(carSelected.getId());
    // result = true;
    // break;
    // }
    // }
    // return result;
    // }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        restoreSavedState(savedInstanceState);

        if (!isInitialized) {

            // NOOP for now
            isInitialized = true;
        }
        registerForContextMenu(getListView());

        getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("curChoiceList", mPositionChecked);
        outState.putInt("shownChoiceList", mPositionShown);
    }

    public void refreshData(final boolean forceRefresh) {
        // refresh = forceRefresh;
        fillData();
    }

    public void fillData() {

        final GetDataTask asyncTask = new GetDataTask();
        asyncTask.execute((Void) null);
    }

    public static class ViewHolder {
        public TextView[] textView = new TextView[7];

        ImageView         icon1;
        ImageView         icon2;
    }

    final static String[] COLUMNS = { "name", "licence" };
    final static int[]    FIELDS  = { R.id.carName, R.id.carLicence };

    public class GetDataTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(final Void... params) {

            final Cursor cursor = CoreInfoHolder.getInstance().getDbManager()
                    .getDatabase().getCarListCursor();
            getActivity().startManagingCursor(cursor);
            return cursor;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(final Cursor cursor) {

            if (cursor != null) {
                setListAdapter(new SimpleCursorAdapter(CoreInfoHolder
                        .getInstance().getContext(), R.layout.car_item, cursor,
                        CarListFragment.COLUMNS, CarListFragment.FIELDS));

                final String text = CoreInfoHolder.getInstance().getContext()
                        .getText(R.string.nr_of_entries).toString();
                nrOfEntries.setText(text + " " + cursor.getCount());

            }
        }
    }

    private void deleteCar(final int id) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity());
        builder.setMessage(R.string.warning_delete_car)
                .setCancelable(false)
                .setPositiveButton(R.string.dialogYES,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {

                                // CoreInfoHolder.getInstance()
                                // .getDbManager(getActivity()).beginTransaction();

                                CoreInfoHolder.getInstance().getDbManager()
                                        .deleteCar(carSelected.getId());
                                // CoreInfoHolder.getInstance()
                                // .getDbManager(getActivity()).commitTransaction();

                                dialog.dismiss();
                                fillData();
                            }
                        })
                .setNegativeButton(R.string.dialogNO,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    private void editCar(final int id) {
        final DialogFragment df = CarFragment.newInstance(carSelected.getId(),
                carSelected.getName(), carSelected.getLicence(),
                R.string.dlg_car_title, this);
        df.show(getFragmentManager(), "Auto");
    }

    private void addCar() {
        final DialogFragment df = CarFragment.newInstance(-1, "", "",
                R.string.dlg_car_title, this);
        df.show(getFragmentManager(), "Auto");
    }

    private void deleteAllCars() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                CoreInfoHolder.getInstance().getContext());
        builder.setMessage(R.string.warning_delete_all_cars)
                .setCancelable(false)
                .setPositiveButton(R.string.dialogYES,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {

                                CoreInfoHolder.getInstance().getDbManager()
                                        .deleteAllCars();

                                dialog.dismiss();
                                fillData();
                            }
                        })
                .setNegativeButton(R.string.dialogNO,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    private void pause() {
        // NOOP for now
    }

    private void resume() {
        fillData();
    }

    @Override
    public void pageGetsActivated() {
        resume();
    }

    @Override
    public void pageGetsDeactivated() {
        pause();
    }

}
