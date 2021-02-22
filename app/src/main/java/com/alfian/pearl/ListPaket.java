package com.alfian.pearl;

class ListPaket {
    String nama_treatment, keterangan, photo_treatment;

    public ListPaket()
    {
    }

    public ListPaket(String nama_treatment, String photo_treatment)
    {
        this.nama_treatment = nama_treatment;
        this.keterangan = keterangan;
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
    public String getPhoto_treatment()
    {
        return photo_treatment;
    }

    public void setPhoto_treatment(String photo_treatment)
    {
        this.photo_treatment = photo_treatment;
    }

}
