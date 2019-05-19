package com.annakhuseinova.FileUploaderApp.model;

public class FileInfo {
    private String fileName;
    private String fileSize;
    private String lastModificationDate;
    private String downloadUrl;
    private String deleteUrl;

    public FileInfo(String fileName, String fileSize, String lastModificationDate, String downloadUrl, String deleteUrl) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.lastModificationDate = lastModificationDate;
        this.downloadUrl = downloadUrl;
        this.deleteUrl = deleteUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }
}
