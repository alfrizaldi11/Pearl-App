package com.alfian.pearl;

class ListTreatment {

    String nama_treatment, keterangan, photo_treatment;
    Integer harga_treatment;

    public ListTreatment()
    {
    }

    //membuat konstruktor sesuai nama kelas yaitu ListTreatment
    public ListTreatment(String nama_treatment, String keterangan, Integer harga_treatment, String photo_treatment)
    {
        this.nama_treatment = nama_treatment;
        this.keterangan = keterangan;
        this.harga_treatment = harga_treatment;
        this.photo_treatment = photo_treatment;
    }

    //Methode Getter & Setter
    public String getNama_treatment()
    {
        return nama_treatment;
    }

    public void setNama_treatment(String nama_treatment)
    {
        this.nama_treatment = nama_treatment;
    }

    public String getKeterangan()
    {
        return keterangan;
    }

    public void setKeterangan(String keterangan)
    {
        this.keterangan = keterangan;
    }

    public Integer getHarga_treatment()
    {
        return harga_treatment;
    }

    public void setHarga_treatment (Integer harga_treatment)
    {
        this.harga_treatment = harga_treatment;
    }

    public String getPhoto_treatment()
    {
        return photo_treatment;
    }

    public void setPhoto_treatment(String photo_treatment)
    {
        this.photo_treatment = photo_treatment;
    }
}
