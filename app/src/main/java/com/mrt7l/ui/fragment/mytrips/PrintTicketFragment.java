package com.mrt7l.ui.fragment.mytrips;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mrt7l.BuildConfig;
import com.mrt7l.R;
import com.mrt7l.databinding.PrintTicketFragment2Binding;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.DownloadTask;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.fragment.reservation.addpassengers.PassengerPriceModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PrintTicketFragment extends Fragment implements PrintTicketInterface {

    private PrintTicketViewModel mViewModel;

    public static PrintTicketFragment newInstance() {
        return new PrintTicketFragment();
    }
    PrintTicketFragment2Binding binding;
    private CurrentOrdersResponse.Mrt7alBean.DataBean dataModel;
    private Bitmap barcodeImage;
     @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.print_ticket_fragment2, container, false);
        mViewModel = new ViewModelProvider(this).get(PrintTicketViewModel.class);
        mViewModel.init(this);
        if (getArguments()!=null) {
            String json = getArguments().getString("data");
            dataModel = new Gson().fromJson(json, CurrentOrdersResponse.Mrt7alBean.DataBean.class);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            try {
                barcodeImage = barcodeEncoder.encodeBitmap(json, BarcodeFormat.QR_CODE,
                        400, 400, hints);
                if (barcodeImage != null)
                binding.qrImage.setImageBitmap(barcodeImage);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            if (dataModel.getTrip_date().getCompany() != null) {
                binding.companyName.setText(dataModel.getTrip_date().getCompany().getName());
                Glide.with(requireActivity()).load("https://administrator.mrt7al.com/"
                        +dataModel.getTrip_date().getCompany().getLogo_pic())
                        .into(binding.companyImage);
                binding.busType.setText(getString(R.string.txt_BusType) +
                        " " +dataModel.getTrip_date().getBus_type());
                binding.tripDate.setText(formatDate(dataModel.getTrip_date().getDatetime_from()));
                if (dataModel.getTrip_date().getCompany().getReservation_policy() != null)
                    binding.content.setText(dataModel.getTrip_date().getCompany().getReservation_policy());
            }
            binding.tvStartTime.setText(formatTime(dataModel.getTrip_date().getDatetime_to()));
            binding.tvEndTime.setText(formatTime(dataModel.getTrip_date().getDatetime_from()));
            binding.tvTotalDuration.setText(String.valueOf(dataModel.getTrip_date().getWaitingTime()));
            binding.tvStartTimeAA.setText(dataModel.getTrip_date().getFrom_city().getName());
            binding.tvEndTimeAA.setText(dataModel.getTrip_date().getTo_city().getName());
            binding.tripDate.setText(formatDate(dataModel.getTrip_date().getDatetime_from()));
            binding.tripNumber.setText(dataModel.getTripID());
            binding.reservationNumber.setText(String.valueOf(dataModel.getRes_passangers().get(0).getReservation_id()));
            binding.ticketNum.setText(dataModel.getTicket_number());
            if (dataModel.getTrip_date().getStation()!=null)
            binding.startStation.setText(dataModel.getTrip_date().getStation().getName());
            ArrayList<PassengerPriceModel> priceModels = new ArrayList<>();
            priceModels.add(new PassengerPriceModel(getString(R.string.passenger_type),
                    getString(R.string.count), getString(R.string.text_ticket_price)));
            if (dataModel.getAdults_count() != 0) {
                priceModels.add(new PassengerPriceModel(getString(R.string.adult),
                        String.valueOf(dataModel.getAdults_count()), String.valueOf(
                        dataModel.getTrip_date().getPrice())));
            } else {
                priceModels.add(new PassengerPriceModel(getString(R.string.adult),
                        String.valueOf(dataModel.getAdults_count()), "0"));
            }
            if (dataModel.getChildren_count() != 0) {
                priceModels.add(new PassengerPriceModel(getString(R.string.child),
                        String.valueOf(dataModel
                                .getChildren_count()), String.valueOf(dataModel
                        .getTrip_date().getChild_price())));
            } else {
                priceModels.add(new PassengerPriceModel(getString(R.string.child),
                        String.valueOf(dataModel
                                .getChildren_count()), "0"));
            }
          if (dataModel
                  .getBabies_count() != 0) {
              priceModels.add(new PassengerPriceModel(getString(R.string.baby),
                      String.valueOf(dataModel
                              .getBabies_count()), String.valueOf(dataModel
                      .getTrip_date().getBaby_price())));
          } else {
              priceModels.add(new PassengerPriceModel(getString(R.string.baby),
                      String.valueOf(dataModel
                              .getBabies_count()), "0"));
          }
            PassengersPriceAdapter passengersPriceAdapter = new PassengersPriceAdapter(priceModels,requireContext());
            binding.priceRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.priceRecyclerView.setAdapter(passengersPriceAdapter);
        if (dataModel.getRes_passangers().size() > 0){
            ArrayList<CurrentOrdersResponse.Mrt7alBean.DataBean.ResPassangersBean.SubPassangerBean>
                    subPassangerBeans = new ArrayList<>();
            subPassangerBeans.add(new CurrentOrdersResponse.Mrt7alBean.DataBean.ResPassangersBean.SubPassangerBean());
              for (int i=0;i<dataModel.getRes_passangers().size();i++){
                if (dataModel.getRes_passangers().get(i).getSub_passanger() != null){
                    subPassangerBeans.add(dataModel.getRes_passangers().get(i).getSub_passanger() );
                }
            }
            PassengersAdapter passengersAdapter = new PassengersAdapter(subPassangerBeans,requireContext());
            binding.passengersRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.passengersRecyclerView.setAdapter(passengersAdapter);
            binding.totalPrice.setText(String.valueOf(dataModel.getTotal_price() + Integer.parseInt(
                    dataModel.getDiscountPromo())))  ;
            binding.voucherDiscount.setText(dataModel.getDiscountPromo());
            binding.finalTotal.setText(String.valueOf(dataModel.getTotal_price()));
        }
        binding.tripCodeNumber.setVisibility(View.GONE);
     }
    binding.okButton.setOnClickListener(v -> {
        DialogsHelper.showSimpleProgressDialog(getString(R.string.downloading),requireActivity());
        mViewModel.requestFile(new PreferenceHelper(requireActivity()).getTOKEN(),
                dataModel.getId());
//        layoutToImage(binding.allViews);
    });
            return binding.getRoot();
    }

    public String formatDate(String date){
        String theDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd ", Locale.ENGLISH);
        try {
            Date datse = format.parse(date.replace("T" , " ").replace("+03:00","") );
            assert datse != null;
            theDate = formats.format(datse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theDate;
    }

    public String formatTime(String date){
        String theDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formats = new SimpleDateFormat("HH:mm:ss ", Locale.ENGLISH);
        try {
            Date datse = format.parse(date.replace("T" , " ").replace("+03:00","") );
            assert datse != null;
            theDate = formats.format(datse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theDate;
    }

    String dirpath;
    public void layoutToImage(View view) {
        // get view group using reference
//        relativeLayout = (RelativeLayout) view.findViewById(R.id.print);
        // convert view group to bitmap
        binding.allViews.setDrawingCacheEnabled(true);
        binding.allViews.buildDrawingCache();
        Bitmap bm = binding.allViews.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.DIRECTORY_DOWNLOADS + File.separator + "image.jpg");
        try {
            boolean s = f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            imageToPDF();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.DIRECTORY_DOWNLOADS;
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.DIRECTORY_DOWNLOADS + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(requireActivity(),
                    "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    @Override
    public void handleError(Throwable t) {
        DialogsHelper.removeSimpleProgressDialog();
    }
    String fileTitle;
    String fileUrl = "";
    @Override
    public void onFileRequested(boolean isSuccess, String message,String url) {
        if (isSuccess) {
            fileUrl = url;
            checkPermission();
        } else {
            DialogsHelper.removeSimpleProgressDialog();
        }
    }
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
//            Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            downloadFile(fileUrl); }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
//            Toast.makeText(requireActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };
    private void checkPermission() {
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("من فضلك قم بالسماح بتصريح الذاكرة لتتمكن من ارفاق صورة الفاتورة")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    public void onFileDownloaded(boolean isSuccess) {

    }

    private void  downloadFile(String url){
//        fileTitle = url.replace("https://administrator.mrt7al.com/library/orderBill/","");
//        Log.v("filettiel", fileTitle);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new DownloadTask(requireContext(),url) ;
//        }
//        requireActivity().registerReceiver(onComplete,
//                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//
//        URL ll = null;
//        try {
//            ll = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(ll + ""));
//        request.setTitle(fileTitle);
//        request.setMimeType("application/pdf");
//        request.allowScanningByMediaScanner();
//        request.setAllowedOverMetered(true);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
//                "/mart7alFiles/" + fileTitle);
//        downloadManager = (DownloadManager) requireActivity().
//                getSystemService(DOWNLOAD_SERVICE);
//
//        mDownloadReference = downloadManager.enqueue(request);

    }
    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            try {
                String pathNAme = Environment.
                        getExternalStoragePublicDirectory(Environment.
                                DIRECTORY_DOWNLOADS)
                          + "/mart7alFiles/"+fileTitle;
                File folder = new File(pathNAme, "pdf");
                folder.mkdir();

                Log.v("dpath",pathNAme);
                File mediaStorageDir = requireContext().getExternalFilesDir(null);
                File file = new File(pathNAme);
                boolean s = false;
                if (!file.exists())
                     s = file.mkdir();
                Log.v("ddaaaaaa",String.valueOf(s));
                Intent pdfIntent = new Intent("com.adobe.reader");
                pdfIntent.setType("application/pdf");
                pdfIntent.setAction(Intent.ACTION_VIEW);
//                Uri uri = Uri.fromFile(file);
                Uri uri = FileProvider.getUriForFile(requireActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        file);
                pdfIntent.setDataAndType(uri, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(pdfIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };
    private void openFile(String file) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setDataAndType(Uri.fromFile(new File(file)), "application/pdf");//this is for pdf file. Use appropreate mime type
            startActivity(Intent.createChooser(i,"Share Bill "));
        } catch (Exception e) {
            Toast.makeText(requireActivity(),
                    "No pdf viewing application detected. File saved in download folder",Toast.LENGTH_SHORT).show();
        }
    }
}