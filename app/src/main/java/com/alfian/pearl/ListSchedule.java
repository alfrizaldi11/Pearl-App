package com.alfian.pearl;

class ListSchedule {
    String nama_treatment, keterangan, photo_treatment, id_treatment, catatan;
    Integer harga_treatment, waktu;

    public ListSchedule()
    {
    }

    //membuat konstruktor sesuai nama kelas yaitu ListTreatment
    public ListSchedule(String nama_treatment, String keterangan, Integer harga_treatment, String photo_treatment, Integer waktu, String id_treatment, String catatan)
    {
        this.nama_treatment = nama_treatment;
        this.keterangan = keterangan;
        this.harga_treatment = harga_treatment;
        this.photo_treatment = photo_treatment;
        this.waktu = waktu;
        this.id_treatment = id_treatment;
        this.catatan = catatan;
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
    public Integer getWaktu()
    {
        return waktu;
    }

    public void setWaktu(Integer waktu)
    {
        this.waktu = waktu;
    }
    public String getId_treatment()
    {
        return id_treatment;
    }

    public void setId_treatment(String id_treatment)
    {
        this.id_treatment = id_treatment;
    }
    public String getCatatan()
    {
        return catatan;
    }

    public void setCatatan(String catatan)
    {
        this.catatan = catatan;
    }
}
