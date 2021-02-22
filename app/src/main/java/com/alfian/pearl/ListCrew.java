package com.alfian.pearl;

class ListCrew {

    String nama_crew, photo_crew, tanggal;

    public ListCrew()
    {
    }

    //membuat konstruktor sesuai nama kelas yaitu ListTreatment
    public ListCrew(String nama_crew, String photo_crew, String tanggal)
    {
        this.nama_crew = nama_crew;
        this.photo_crew = photo_crew;
        this.tanggal = tanggal;
    }

    //Methode Getter & Setter
    public String getNama_crew()
    {
        return nama_crew;
    }

    public void setNama_crew(String nama_crew)
    {
        this.nama_crew = nama_crew;
    }

    public String getPhoto_crew()
    {
        return photo_crew;
    }

    public void setPhoto_crew(String photo_crew)
    {
        this.photo_crew = photo_crew;
    }

    public String getTanggal()
    {
        return tanggal;
    }

    public void setTanggal(String tanggal)
    {
        this.tanggal = tanggal;
    }
}
