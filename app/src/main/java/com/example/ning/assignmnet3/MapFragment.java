package com.example.ning.assignmnet3;

import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MapFragment extends Fragment implements View.OnClickListener{
    View vMapUnit;
    private MapboxMap mMapboxMap;
    private MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMapUnit = inflater.inflate(R.layout.fragment_map_unit, container, false);
        mMapView = (MapView) vMapUnit.findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        String name = getActivity().getIntent().getExtras().getString("username");
        String cred = "";
        List<Address> address;
        Geocoder coder = new Geocoder(getContext());
        String addressStr = "";
        LatLng SAN_FRAN = null;

        try {
            GetUserIdAsyncTask getUserIdAsyncTask = new GetUserIdAsyncTask();
            cred = getUserIdAsyncTask.execute(name).get();
            JSONArray credJsonArray = new JSONArray(cred);

            JSONObject credObject = credJsonArray.getJSONObject(0);
            String userId = credObject.getString("userId");
            JSONObject obj = new JSONObject(userId);
            addressStr = obj.getString("address");

            address = coder.getFromLocationName(addressStr,5);
            Address location = address.get(0);

                SAN_FRAN = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng finalSAN_FRAN = SAN_FRAN;
                String finalAddressStr = addressStr;

                mMapView.getMapAsync(new OnMapReadyCallback() {

                    @Override
                    public void onMapReady(MapboxMap mapboxMap) {
                        mMapboxMap = mapboxMap;
                        mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(finalSAN_FRAN, 11));

                        addMarker(mapboxMap, finalSAN_FRAN, "Home", "The Simpsons");

                        GetParkAsyncTask getParkAsyncTask =new GetParkAsyncTask();
                        try {
                            String jsStr = getParkAsyncTask.execute(finalAddressStr).get();
                            JSONObject originalJson = new JSONObject(jsStr);
                            String arrayStr = originalJson.getString("searchResults");
                            JSONArray credJsonArray = new JSONArray(arrayStr);
                            JSONObject resultObj = credJsonArray.getJSONObject(0);
                            String name = resultObj.getString("name");
                            JSONObject field = resultObj.getJSONObject("fields");
                            JSONObject mqap = field.getJSONObject("mqap_geography");
                            JSONObject latLng = mqap.getJSONObject("latLng");
                            double lng = Double.parseDouble(latLng.getString("lng"));
                            double lat = Double.parseDouble(latLng.getString("lat"));
                            LatLng parkPosition = new LatLng(lat, lng);
                            IconFactory iconFactory = IconFactory.getInstance(getActivity());
                            Icon icon = iconFactory.fromResource(R.drawable.marker);
                            addParkMarker(mapboxMap,parkPosition,"Park", name, icon);


                        }catch (InterruptedException e){
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch(InterruptedException e){
                e.printStackTrace();
            } catch(ExecutionException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        return vMapUnit;
    }

    private void addMarker(MapboxMap mapboxMap,LatLng SAN_FRAN, String tittle, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SAN_FRAN);
        markerOptions.title(tittle);
        markerOptions.snippet(snippet);
        mapboxMap.addMarker(markerOptions);
    }

    private void addParkMarker(MapboxMap mapboxMap, LatLng SAN_FRAN, String tittle, String snippet, Icon icon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SAN_FRAN);
        markerOptions.title(tittle);
        markerOptions.snippet(snippet);
        markerOptions.icon(icon);
        mapboxMap.addMarker(markerOptions);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mMapView!=null)
            mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        if(mMapView!=null)
            mMapView.onPause();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mMapView!=null)
            mMapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mMapView!=null)
            mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

    }

    private class GetUserIdAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findCredByUsername(params[0]);
        }

        @Override
        protected void onPostExecute(String recordPassword) {
        }
    }

    private class GetParkAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.parkSearch(params[0]);
        }

        @Override
        protected void onPostExecute(String address) {


        }
    }
}