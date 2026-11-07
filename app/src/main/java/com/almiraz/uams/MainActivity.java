                    } else {
                        pendingWebPermission = request;
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                CAMERA_PERMISSION_REQUEST);
                    }
                });
            }
        });

        webView.setDownloadListener((url, userAgent, contentDisposition, mimeType,
                contentLength) -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Unable to open download", Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState == null) webView.loadUrl(START_URL);
        else webView.restoreState(savedInstanceState);
    }

    private class PrintBridge {
        @JavascriptInterface
        public void printPage() {
            runOnUiThread(() -> printCurrentPage());
        }
    }

    private void printCurrentPage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || webView == null) {
            Toast.makeText(this, "Printing is not supported on this device",
                    Toast.LENGTH_LONG).show();
            return;
        }

        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        if (printManager == null) {
            Toast.makeText(this, "Android print service is unavailable",
                    Toast.LENGTH_LONG).show();
            return;
        }

        String jobName = webView.getTitle();
        if (jobName == null || jobName.trim().isEmpty()) jobName = "UAMS Document";

        try {
            PrintAttributes attributes = new PrintAttributes.Builder()
                    .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                    .build();
            printManager.print(jobName,
                    webView.createPrintDocumentAdapter(jobName), attributes);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open print dialog: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST && pendingWebPermission != null) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pendingWebPermission.grant(pendingWebPermission.getResources());
            } else {
                pendingWebPermission.deny();
                Toast.makeText(this, "Camera permission is required for scanning",
                        Toast.LENGTH_LONG).show();
            }
            pendingWebPermission = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_REQUEST && filePathCallback != null) {
            Uri[] results = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
            filePathCallback.onReceiveValue(results);
            filePathCallback = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (webView != null) webView.saveState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (filePathCallback != null) {
            filePathCallback.onReceiveValue(null);
            filePathCallback = null;
        }
        if (pendingWebPermission != null) {
            pendingWebPermission.deny();
            pendingWebPermission = null;
        }
        if (webView != null) {
            webView.removeJavascriptInterface("AndroidPrint");
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
