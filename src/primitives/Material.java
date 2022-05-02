package primitives;
/**
 *Material
 *
 * @author Idan and Eliyahu
 */
public class Material {
    private  int nShininess; // amount of shininess
    private Double3 kD = Double3.ZERO ,kS = Double3.ZERO ;
    public Double3 kT= Double3.ZERO  ,kR= Double3.ZERO  ;
  
    public Double3 getKT() {
        return this.kT;
    }
    /**
     *Material setKT
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this ; 
    }

    public Double3 getKR() {
        return this.kR;
    }
    /**
     *Material setKR
     */
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this ;
    }

    public Double3 getKd(){
        return kD ;
    } // getKd

    public Double3 getKs(){
        return kS;
    } // getKs

    public int getnShininess(){
        return nShininess ;
    }
    /**
     *Material setkD
     */
    public Material setkD(Double3 kD){
        this.kD=kD;
        return this ;
    }
    /**
     *Material setkS
     */
    public Material setkS(Double3 kS){
        this.kS=kS;
        return this ;
    }
    /**
     *Material setnShininess shininess amount
     */
    public Material setnShininess(int nShininess){
        this.nShininess=nShininess;
        return this;
    }




}
