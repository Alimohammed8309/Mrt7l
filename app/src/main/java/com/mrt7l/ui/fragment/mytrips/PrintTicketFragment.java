package com.mrt7l.ui.fragment.mytrips;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Handler;
import android.os.Looper;
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
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mrt7l.R;
import com.mrt7l.databinding.PrintTicketFragment2Binding;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.DownloadTask;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.fragment.reservation.addpassengers.PassengerPriceModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintTicketFragment extends Fragment implements PrintTicketInterface {

    private PrintTicketViewModel mViewModel;

    public static PrintTicketFragment newInstance() {
        return new PrintTicketFragment();
    }
    private ActivityResultLauncher<Intent> createFileLauncher;
    PrintTicketFragment2Binding binding;
    private CurrentOrdersResponse.Mrt7alBean.DataBean dataModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.print_ticket_fragment2, container, false);
        mViewModel = new ViewModelProvider(this).get(PrintTicketViewModel.class);
         // Initialize ActivityResultLauncher
//         createFileLauncher = registerForActivityResult(
//                 new ActivityResultContracts.StartActivityForResult(),
//                 result -> handleFileCreationResult(result.getResultCode(), result.getData())
//         );
        mViewModel.init(this);
        if (getArguments()!=null) {
            String json = getArguments().getString("data");
            dataModel = new Gson().fromJson(json, CurrentOrdersResponse.Mrt7alBean.DataBean.class);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            try {
                Bitmap barcodeImage = barcodeEncoder.encodeBitmap(json, BarcodeFormat.QR_CODE,
                        400, 400, hints);
                if (barcodeImage != null){
                binding.qrImage.setImageBitmap(barcodeImage);
                }
            } catch (WriterException e) {
//                //e.printStackTrace();
            }

            if (dataModel.getTrip_date().getCompany() != null) {
                binding.companyName.setText(dataModel.getTrip_date().getCompany().getName());
                Glide.with(requireActivity()).load("https://administrator.mrt7al.com/"
                        +dataModel.getTrip_date().getCompany().getLogo_pic())
                        .into(binding.companyImage);
                binding.busType.setText(String.format(getString(R.string.bus_type_format),
                        getString(R.string.txt_BusType), dataModel.getTrip_date().getBus_type()));
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
            if (dataModel.getTrip_date().getStation()!=null) {
                binding.startStation.setText(dataModel.getTrip_date().getStation().getName());
            }
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
        if (!dataModel.getRes_passangers().isEmpty()){
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
            //e.printStackTrace();
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
            //e.printStackTrace();
        }
        return theDate;
    }

    String dirpath;
//    public void layoutToImage(View view) {
//        // get view group using reference
////        relativeLayout = (RelativeLayout) view.findViewById(R.id.print);
//        // convert view group to bitmap
//        binding.allViews.setDrawingCacheEnabled(true);
//        binding.allViews.buildDrawingCache();
//        Bitmap bm = binding.allViews.getDrawingCache();
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/jpeg");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//
//        File f = new File(Environment.DIRECTORY_DOWNLOADS + File.separator + "image.jpg");
//        try {
//            boolean s = f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//        } catch (IOException e) {
//            //e.printStackTrace();
//        }
//        try {
//            imageToPDF();
//        } catch (FileNotFoundException e) {
//            //e.printStackTrace();
//        }
//    }
//    public void imageToPDF() throws FileNotFoundException {
//        try {
//            Document document = new Document();
//            dirpath = android.os.Environment.DIRECTORY_DOWNLOADS;
//            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //  Change pdf's name.
//            document.open();
//            Image img = Image.getInstance(Environment.DIRECTORY_DOWNLOADS + File.separator + "image.jpg");
//            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
//                    - document.rightMargin() - 0) / img.getWidth()) * 100;
//            img.scalePercent(scaler);
//            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
//            document.add(img);
//            document.close();
//            Toast.makeText(requireActivity(),
//                    "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//
//        }
//    }

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
            fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);//Create file name by picking download file name from URL
            downloadAndOpenFile(requireContext(),url, fileName, "application/pdf");
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
//            //e.printStackTrace();
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
//    BroadcastReceiver onComplete=new BroadcastReceiver() {
//        public void onReceive(Context ctxt, Intent intent) {
//            try {
//                String pathNAme = Environment.
//                        getExternalStoragePublicDirectory(Environment.
//                                DIRECTORY_DOWNLOADS)
//                          + "/mart7alFiles/"+fileTitle;
//                File folder = new File(pathNAme, "pdf");
//                folder.mkdir();
//
//                Log.v("dpath",pathNAme);
//                File mediaStorageDir = requireContext().getExternalFilesDir(null);
//                File file = new File(pathNAme);
//                boolean s = false;
//                if (!file.exists())
//                     s = file.mkdir();
//                Log.v("ddaaaaaa",String.valueOf(s));
//                Intent pdfIntent = new Intent("com.adobe.reader");
//                pdfIntent.setType("application/pdf");
//                pdfIntent.setAction(Intent.ACTION_VIEW);
////                Uri uri = Uri.fromFile(file);
//                Uri uri = FileProvider.getUriForFile(requireActivity(),
//                        BuildConfig.APPLICATION_ID + ".provider",
//                        file);
//                pdfIntent.setDataAndType(uri, "application/pdf");
//                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(pdfIntent);
//            } catch (Exception e) {
//                //e.printStackTrace();
//            }
//        }
//
//    };
//    private void openFile(String file) {
//        try {
//            Intent i = new Intent(Intent.ACTION_SEND);
//            i.setDataAndType(Uri.fromFile(new File(file)), "application/pdf");//this is for pdf file. Use appropreate mime type
//            startActivity(Intent.createChooser(i,"Share Bill "));
//        } catch (Exception e) {
//            Toast.makeText(requireActivity(),
//                    "No pdf viewing application detected. File saved in download folder",Toast.LENGTH_SHORT).show();
//        }
//    }
     private String fileName;

    public void downloadAndOpenFile(String url, String name, String type) {
        this.fileUrl = url;
        this.fileName = name;

        // Create file creation intent
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);

        // Launch SAF file picker
        createFileLauncher.launch(intent);
    }
    public void downloadAndOpenFile(Context context, String fileUrl, String fileName, String mimeType) {
        new Thread(() -> {
            try {
                // Download file
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                File file = new File(context.getCacheDir(), fileName);
                InputStream input = connection.getInputStream();
                FileOutputStream output = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

                output.close();
                input.close();
                connection.disconnect();

                // Open file
                openFile(context, file, mimeType);
                DialogsHelper.removeSimpleProgressDialog();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void openFile(Context context, File file, String mimeType) {
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, mimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No app found to open this file.", Toast.LENGTH_LONG).show();
        }
    }

    private void handleFileCreationResult(int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri destinationUri = data.getData();
            downloadFileToUri(destinationUri);
        } else {
            Toast.makeText(requireContext(), "File creation canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadFileToUri(Uri destinationUri) {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()){
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                try (InputStream input = connection.getInputStream();
                     OutputStream output = requireActivity().getContentResolver().openOutputStream(destinationUri)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        Objects.requireNonNull(output).write(buffer, 0, bytesRead);
                    }
                }

                handler.post(() -> {
//                    openFile(destinationUri);
                    Toast.makeText(requireContext(), "Download complete", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                executor.shutdownNow();
            handler.post(() ->
                        Toast.makeText(requireContext(), "Download failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

        });
        }
    }

//    private void openFile(Uri fileUri) {
//        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
//        viewIntent.setDataAndType(fileUri, requireContext().getContentResolver().getType(fileUri));
//        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        // Verify there's an app to handle the file
//        PackageManager pm = requireContext().getPackageManager();
//        if (viewIntent.resolveActivity(pm) != null) {
//            startActivity(viewIntent);
//        } else {
//            // Fallback to share intent or download manager
//            Intent shareIntent = Intent.createChooser(
//                    new Intent(Intent.ACTION_VIEW).setData(fileUri),
//                    "Open File"
//            );
//            try {
//                startActivity(shareIntent);
//            } catch (ActivityNotFoundException ex) {
//                Toast.makeText(requireContext(), "No app available to open this file", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


}