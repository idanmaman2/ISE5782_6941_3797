package primitives;

public class Material {
    private  int nShininess;
    private Double3 kD = Double3.ZERO ,kS = Double3.ZERO ;
    
    public Double3 getKd(){
        return kD ;
    }

    public Double3 getKs(){
        return kS;
    }

    public int getnShininess(){
        return nShininess ;
    }

    public Material setkD(Double3 kD){
        this.kD=kD;
        return this ;
    }

    public Material setkS(Double3 kS){
        this.kS=kS;
        return this ;
    }

    public Material setnShininess(int nShininess){
        this.nShininess=nShininess;
        return this;
    }




}
