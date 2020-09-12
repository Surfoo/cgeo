package cgeo.geocaching.maps.google.v2;

import cgeo.geocaching.R;
import cgeo.geocaching.activity.ActivityMixin;
import cgeo.geocaching.activity.FilteredActivity;
import cgeo.geocaching.maps.AbstractMap;
import cgeo.geocaching.maps.CGeoMap;
import cgeo.geocaching.maps.interfaces.MapActivityImpl;
import cgeo.geocaching.settings.Settings;
import cgeo.geocaching.utils.IndividualRouteUtils;
import cgeo.geocaching.utils.MapDownloadUtils;
import cgeo.geocaching.utils.TrackUtils;
import static cgeo.geocaching.maps.google.v2.GoogleMapUtils.isGoogleMapsAvailable;
import static cgeo.geocaching.settings.Settings.MAPROTATION_AUTO;
import static cgeo.geocaching.settings.Settings.MAPROTATION_MANUAL;
import static cgeo.geocaching.settings.Settings.MAPROTATION_OFF;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

public class GoogleMapActivity extends Activity implements MapActivityImpl, FilteredActivity {


    private final AbstractMap mapBase;

    public GoogleMapActivity() {
        mapBase = new CGeoMap(this);
    }

    public void setTheme(final int resid) {
        if (Settings.isLightSkin()) {
            super.setTheme(R.style.cgeo_gmap_light);
        } else {
            super.setTheme(R.style.cgeo_gmap);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(final Bundle icicle) {
        mapBase.onCreate(icicle);
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        mapBase.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        mapBase.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        mapBase.onDestroy();
    }

    @Override
    protected void onPause() {
        mapBase.onPause();
    }

    @Override
    protected void onResume() {
        mapBase.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu) {
        return mapBase.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        return mapBase.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(@NonNull final Menu menu) {
        return mapBase.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        mapBase.onStop();
    }

    @Override
    public void superOnCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean superOnCreateOptionsMenu(@NonNull final Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void superOnDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean superOnOptionsItemSelected(@NonNull final MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateUp(final View view) {
        ActivityMixin.navigateUp(this);
    }

    @Override
    public void superOnResume() {
        super.onResume();
    }

    @Override
    public void superOnStop() {
        super.onStop();
    }

    @Override
    public void superOnPause() {
        super.onPause();
    }

    @Override
    public boolean superOnPrepareOptionsMenu(@NonNull final Menu menu) {
        final boolean result = super.onPrepareOptionsMenu(menu);
        final boolean isGoogleMapsAvailable = isGoogleMapsAvailable(this);

        menu.findItem(R.id.menu_map_rotation).setVisible(isGoogleMapsAvailable);
        if (isGoogleMapsAvailable) {
            final int mapRotation = Settings.getMapRotation();
            switch (mapRotation) {
                case MAPROTATION_OFF:
                    menu.findItem(R.id.menu_map_rotation_off).setChecked(true);
                    break;
                case MAPROTATION_MANUAL:
                    menu.findItem(R.id.menu_map_rotation_manual).setChecked(true);
                    break;
                case MAPROTATION_AUTO:
                    menu.findItem(R.id.menu_map_rotation_auto).setChecked(true);
                    break;
                default:
                    break;
            }
        }
        TrackUtils.onPrepareOptionsMenu(menu);

        return result;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TrackUtils.onActivityResult(this, requestCode, resultCode, data, mapBase::setTracks);
        IndividualRouteUtils.onActivityResult(this, requestCode, resultCode, data, mapBase::reloadIndividualRoute);
        MapDownloadUtils.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void showFilterMenu(final View view) {
        // do nothing, the filter bar only shows the global filter
    }

}
