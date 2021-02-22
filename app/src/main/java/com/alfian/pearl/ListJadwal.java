package com.alfian.pearl;

class ListJadwal {
    String nama_crew, tanggal;

    public ListJadwal()
    {
    }

    public ListJadwal(String nama_crew, String tanggal)
    {
        this.tanggal = tanggal;
        this.nama_crew = nama_crew;
    }

    //Methode Getter & Setter
    public String getTanggal()
    {
        return tanggal;
    }

    public void setTanggal(String tanggal)
    {
        this.tanggal = tanggal;
    }
    public String getNama_crew()
    {
        return nama_crew;
    }

    public void setNama_crew(String nama_crew)
    {
        this.nama_crew = nama_crew;
    }

}
