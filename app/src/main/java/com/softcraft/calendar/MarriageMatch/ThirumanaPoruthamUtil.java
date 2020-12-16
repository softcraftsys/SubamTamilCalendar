package com.softcraft.calendar.MarriageMatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirumanaPoruthamUtil {
    public static final int AAILLYA_NATCHATHIRAM = 9;
    public static final int ANUSHA_NATCHATHIRAM = 17;
    public static final int ASTHA_NATCHATHIRAM = 13;
    public static final int ASWINI_NATCHATHIRAM = 1;
    public static final int AVITTA_NATCHATHIRAM = 23;
    public static final int BHARANI_NATCHATHIRAM = 2;
    public static final int BUDHAN = 4;
    public static final int CHANDHIRAN = 2;
    public static final int ENEMY = 3;
    public static final int EQUAL = 2;
    public static final int FRIEND = 1;
    public static final int GURU = 5;
    public static final int KADAKA_RAASI = 4;
    public static final int KAETTAI_NATCHATHIRAM = 18;
    public static final int KANNI_RAASI = 6;
    public static final int KIRUTHIKAI_NATCHATHIRAM = 3;
    public static final int KUMBA_RAASI = 11;
    public static final int MAKARA_RAASI = 10;
    public static final int MAKA_NATCHATHIRAM = 10;
    public static final int MEENA_RAASI = 12;
    public static final int MESHA_RAASI = 1;
    public static final int MIDHUNA_RAASI = 3;
    public static final int MIRUGASEERIDA_NATCHATHIRAM = 5;
    public static final int MOOLA_NATCHATHIRAM = 19;
    public static final int NA = 4;
    public static final int NO = 0;
    public static final int NOT_OK = 0;
    public static final int OK = 10;
    public static final int PARTIALLY_OK = 5;
    public static final int POORAADA_NATCHATHIRAM = 20;
    public static final int POORATTAADHI_NATCHATHIRAM = 25;
    public static final int POORA_NATCHATHIRAM = 11;
    public static final int POOSA_NATCHATHIRAM = 8;
    public static final int PUNARPOOSA_NATCHATHIRAM = 7;
    public static final int REVATHI_NATCHATHIRAM = 27;
    public static final int RISHABA_RAASI = 2;
    public static final int ROHINI_NATCHATHIRAM = 4;
    public static final int SADHAYA_NATCHATHIRAM = 24;
    public static final int SANI = 7;
    public static final int SEVVAAI = 3;
    public static final int SIMMA_RAASI = 5;
    public static final int SITHIRAI_NATCHATHIRAM = 14;
    public static final int SOORIYAN = 1;
    public static final int SUKKIRAN = 6;
    public static final int SWATHI_NATCHATHIRAM = 15;
    public static final int THANUSU_RAASI = 9;
    public static final int THIRUVAADHIRAI_NATCHATHIRAM = 6;
    public static final int THIRUVONA_NATCHATHIRAM = 22;
    public static final int THULAAM_RAASI = 7;
    public static final int UTHIRAADA_NATCHATHIRAM = 21;
    public static final int UTHIRATTAADHI_NATCHATHIRAM = 26;
    public static final int UTHIRA_NATCHATHIRAM = 12;
    public static final int VIRUCHAGA_RAASI = 8;
    public static final int VISAAKA_NATCHATHIRAM = 16;
    public static final int YES = 1;
    Integer[] dhevaGanamKondaNatchathiram = new Integer[]{Integer.valueOf(1), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(13), Integer.valueOf(15), Integer.valueOf(17), Integer.valueOf(22), Integer.valueOf(27)};
    Integer[] dhinaPoruthamDifferenceCheckList = new Integer[]{Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(11), Integer.valueOf(13), Integer.valueOf(15), Integer.valueOf(18), Integer.valueOf(20), Integer.valueOf(24), Integer.valueOf(26)};
    Integer[] kandaRajjuNatchathirangal = new Integer[]{Integer.valueOf(4), Integer.valueOf(13), Integer.valueOf(22), Integer.valueOf(6), Integer.valueOf(15), Integer.valueOf(24)};
    Integer[] maanidaGanamKondaNatchathiram = new Integer[]{Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(11), Integer.valueOf(20), Integer.valueOf(25), Integer.valueOf(12), Integer.valueOf(26), Integer.valueOf(21)};
    Integer[] mahendraPoruthamDifferenceCheckList = new Integer[]{Integer.valueOf(4), Integer.valueOf(7), Integer.valueOf(10), Integer.valueOf(13), Integer.valueOf(16), Integer.valueOf(19), Integer.valueOf(22), Integer.valueOf(25)};
    Integer[] ooruRajjuNatchathirangal = new Integer[]{Integer.valueOf(2), Integer.valueOf(11), Integer.valueOf(20), Integer.valueOf(8), Integer.valueOf(17), Integer.valueOf(26)};
    Integer[] paadhaRajjuNatchathirangal = new Integer[]{Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(19), Integer.valueOf(9), Integer.valueOf(18), Integer.valueOf(27)};
    Integer[] raasiAdhibadhi;
    int[][] raasiAdhibadhiMatrix;
    Integer[] raasiPoruthamNotOkDifferenceCheckList = new Integer[]{Integer.valueOf(6), Integer.valueOf(12), Integer.valueOf(2), Integer.valueOf(8)};
    Integer[] raasiPoruthamPartialDifferenceCheckList = new Integer[]{Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11)};
    Integer[] raatchasaGanamKondaNatchathiram = new Integer[]{Integer.valueOf(3), Integer.valueOf(10), Integer.valueOf(16), Integer.valueOf(24), Integer.valueOf(9), Integer.valueOf(23), Integer.valueOf(14), Integer.valueOf(18), Integer.valueOf(19)};
    Integer[] siroRajjuNatchathirangal = new Integer[]{Integer.valueOf(5), Integer.valueOf(14), Integer.valueOf(23)};
    Integer[] udhaaraRajjuNatchathirangal = new Integer[]{Integer.valueOf(3), Integer.valueOf(12), Integer.valueOf(21), Integer.valueOf(16), Integer.valueOf(7), Integer.valueOf(25)};
    Integer[][] vedhaiCombinationMatrix;
    int[][] yoniMatrix;

    private int getNatchathiraDifference(int i, int i2) {
        return i2 > i ? (i2 - i) + 1 : ((27 - i) + i2) + 1;
    }

    private int getRaasiDifference(int i, int i2) {
        return i2 > i ? (i2 - i) + 1 : ((12 - i) + i2) + 1;
    }

    private int getVasiyaPorutham(int i, int i2) {
        return (i == 1 && (i2 == 5 || i2 == 8)) ? 10 : (i == 2 && (i2 == 4 || i2 == 7)) ? 10 : (i == 3 && i2 == 6) ? 10 : (i == 4 && (i2 == 8 || i2 == 9)) ? 10 : (i == 5 && i2 == 10) ? 10 : (i == 6 && (i2 == 2 || i2 == 12)) ? 10 : (i == 7 && i2 == 10) ? 10 : (i == 8 && (i2 == 4 || i2 == 6)) ? 10 : (i == 9 && i2 == 12) ? 10 : (i == 10 && i2 == 11) ? 10 : (i == 11 && i2 == 12) ? 10 : (i == 12 && i2 == 10) ? 10 : 0;
    }

    public ThirumanaPoruthamUtil() {
        Integer[][] r2 = new Integer[30][];
        r2[0] = new Integer[]{Integer.valueOf(1), Integer.valueOf(18)};
        r2[1] = new Integer[]{Integer.valueOf(2), Integer.valueOf(17)};
        r2[2] = new Integer[]{Integer.valueOf(3), Integer.valueOf(16)};
        r2[3] = new Integer[]{Integer.valueOf(4), Integer.valueOf(15)};
        r2[4] = new Integer[]{Integer.valueOf(5), Integer.valueOf(14)};
        r2[5] = new Integer[]{Integer.valueOf(6), Integer.valueOf(22)};
        r2[6] = new Integer[]{Integer.valueOf(7), Integer.valueOf(21)};
        r2[7] = new Integer[]{Integer.valueOf(8), Integer.valueOf(20)};
        r2[8] = new Integer[]{Integer.valueOf(9), Integer.valueOf(19)};
        r2[9] = new Integer[]{Integer.valueOf(10), Integer.valueOf(27)};
        r2[10] = new Integer[]{Integer.valueOf(11), Integer.valueOf(26)};
        r2[11] = new Integer[]{Integer.valueOf(12), Integer.valueOf(25)};
        r2[12] = new Integer[]{Integer.valueOf(13), Integer.valueOf(24)};
        r2[13] = new Integer[]{Integer.valueOf(14), Integer.valueOf(5)};
        r2[14] = new Integer[]{Integer.valueOf(15), Integer.valueOf(4)};
        r2[15] = new Integer[]{Integer.valueOf(16), Integer.valueOf(3)};
        r2[16] = new Integer[]{Integer.valueOf(17), Integer.valueOf(2)};
        r2[17] = new Integer[]{Integer.valueOf(18), Integer.valueOf(1)};
        r2[18] = new Integer[]{Integer.valueOf(19), Integer.valueOf(9)};
        r2[19] = new Integer[]{Integer.valueOf(20), Integer.valueOf(8)};
        r2[20] = new Integer[]{Integer.valueOf(21), Integer.valueOf(7)};
        r2[21] = new Integer[]{Integer.valueOf(22), Integer.valueOf(6)};
        r2[22] = new Integer[]{Integer.valueOf(23), Integer.valueOf(14)};
        r2[23] = new Integer[]{Integer.valueOf(24), Integer.valueOf(13)};
        r2[24] = new Integer[]{Integer.valueOf(25), Integer.valueOf(12)};
        r2[25] = new Integer[]{Integer.valueOf(26), Integer.valueOf(11)};
        r2[26] = new Integer[]{Integer.valueOf(27), Integer.valueOf(10)};
        r2[27] = new Integer[]{Integer.valueOf(5), Integer.valueOf(23)};
        r2[28] = new Integer[]{Integer.valueOf(14), Integer.valueOf(23)};
        r2[29] = new Integer[]{Integer.valueOf(23), Integer.valueOf(5)};
        this.vedhaiCombinationMatrix = r2;
        this.yoniMatrix = new int[][]{new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}, new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0}};
        this.raasiAdhibadhiMatrix = new int[][]{new int[]{1, 1, 4, 3, 1, 2, 2, 3, 3}, new int[]{3, 3, 2, 1, 2, 4, 1, 1, 1}, new int[]{1, 3, 2, 4, 2, 1, 2, 2, 2}, new int[]{1, 4, 2, 1, 2, 2, 2, 3, 3}, new int[]{4, 1, 1, 2, 1, 3, 3, 3, 3}, new int[]{1, 3, 2, 4, 2, 1, 2, 2, 2}, new int[]{3, 3, 2, 1, 2, 4, 1, 1, 1}, new int[]{1, 1, 4, 3, 1, 2, 2, 3, 3}, new int[]{1, 1, 1, 3, 4, 3, 2, 2, 2}, new int[]{3, 3, 3, 1, 2, 1, 4, 1, 1}, new int[]{3, 3, 3, 1, 2, 1, 4, 1, 1}, new int[]{1, 1, 1, 3, 4, 3, 2, 2, 2}};
        this.raasiAdhibadhi = new Integer[]{Integer.valueOf(3), Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(7), Integer.valueOf(5)};
    }

    public Integer[] getOverAllScore(int i, int i2, int i3, int i4) {
        int dhinaPorutham = getDhinaPorutham(i2, i4);
        int ganaPorutham = getGanaPorutham(i2, i4);
        int mahendhraPorutham = getMahendhraPorutham(i2, i4);
        int sthiriDheergaPorutham = getSthiriDheergaPorutham(i2, i4);
        int yoniPorutham = getYoniPorutham(i2, i4);
        int raasiPorutham = getRaasiPorutham(i, i3);
        int raasiAdhibadhiPorutham = getRaasiAdhibadhiPorutham(i, i3);
        i = getVasiyaPorutham(i, i3);
        i3 = getRajjuPorutham(i2, i4);
        i2 = getVedhaiPorutham(i2, i4);
        return new Integer[]{Integer.valueOf(((((((((dhinaPorutham + ganaPorutham) + mahendhraPorutham) + sthiriDheergaPorutham) + yoniPorutham) + raasiPorutham) + raasiAdhibadhiPorutham) + i) + i3) + i2), Integer.valueOf(dhinaPorutham), Integer.valueOf(ganaPorutham), Integer.valueOf(mahendhraPorutham), Integer.valueOf(sthiriDheergaPorutham), Integer.valueOf(yoniPorutham), Integer.valueOf(raasiPorutham), Integer.valueOf(raasiAdhibadhiPorutham), Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(i2)};
    }

    private int getDhinaPorutham(int i, int i2) {
        return Arrays.asList(this.dhinaPoruthamDifferenceCheckList).contains(Integer.valueOf(getNatchathiraDifference(i, i2))) ? 10 : 0;
    }

    private int getGanaPorutham(int i, int i2) {
        if (Arrays.asList(this.raatchasaGanamKondaNatchathiram).contains(Integer.valueOf(i)) || Arrays.asList(this.raatchasaGanamKondaNatchathiram).contains(Integer.valueOf(i2))) {
            return 0;
        }
        if ((Arrays.asList(this.maanidaGanamKondaNatchathiram).contains(Integer.valueOf(i)) && Arrays.asList(this.maanidaGanamKondaNatchathiram).contains(Integer.valueOf(i2))) || (Arrays.asList(this.dhevaGanamKondaNatchathiram).contains(Integer.valueOf(i)) && Arrays.asList(this.dhevaGanamKondaNatchathiram).contains(Integer.valueOf(i2)))) {
            return 10;
        }
        if ((Arrays.asList(this.maanidaGanamKondaNatchathiram).contains(Integer.valueOf(i)) && Arrays.asList(this.dhevaGanamKondaNatchathiram).contains(Integer.valueOf(i2))) || (Arrays.asList(this.dhevaGanamKondaNatchathiram).contains(Integer.valueOf(i)) && Arrays.asList(this.maanidaGanamKondaNatchathiram).contains(Integer.valueOf(i2)))) {
            return 5;
        }
        return 0;
    }

    private int getMahendhraPorutham(int i, int i2) {
        return Arrays.asList(this.mahendraPoruthamDifferenceCheckList).contains(Integer.valueOf(getNatchathiraDifference(i, i2))) ? 10 : 0;
    }

    private int getSthiriDheergaPorutham(int i, int i2) {
        return getNatchathiraDifference(i, i2) > 13 ? 10 : 0;
    }

    private int getYoniPorutham(int i, int i2) {
        return this.yoniMatrix[i - 1][i2 - 1] == 1 ? 10 : 0;
    }

    private int getRaasiPorutham(int i, int i2) {
        int raasiDifference = getRaasiDifference(i, i2);
        int i3 = 10;
        if (!((raasiDifference == 7 && i == 11 && i2 == 5) || (i == 4 && i2 == 10))) {
            i3 = 0;
        }
        if (Arrays.asList(this.raasiPoruthamPartialDifferenceCheckList).contains(Integer.valueOf(raasiDifference))) {
            i3 = 5;
        }
        return Arrays.asList(this.raasiPoruthamNotOkDifferenceCheckList).contains(Integer.valueOf(raasiDifference)) ? 0 : i3;
    }

    private int getRaasiAdhibadhiPorutham(int i, int i2) {
        int i3 = i2 - 1;
        int i4 = i - 1;
        int i5 = this.raasiAdhibadhiMatrix[i4][this.raasiAdhibadhi[i3].intValue() - 1];
        i3 = this.raasiAdhibadhiMatrix[i3][this.raasiAdhibadhi[i4].intValue() - 1];
        if ((i5 == 1 && i3 == 1) || ((i5 == 2 && i3 == 1) || i == i2)) {
            return 10;
        }
        if (i5 == 3 && i3 == 1) {
            return 5;
        }
        return (!(i5 == 2 && i3 == 3) && i5 == 3 && i3 == 3) ? 0 : 0;
    }

    private int getRajjuPorutham(int i, int i2) {
        return getRajjuComparison(i, i2) ? 10 : 0;
    }

    private int getVedhaiPorutham(int i, int i2) {
        List arrayList = new ArrayList();
        for (Object[] asList : this.vedhaiCombinationMatrix) {
            arrayList.add(Arrays.asList(asList));
        }
        if (arrayList.contains(Arrays.asList(new Integer[]{Integer.valueOf(i), Integer.valueOf(i2)}))) {
            return 0;
        }
        return 10;
    }

    private boolean getRajjuComparison(int i, int i2) {
        if (Arrays.asList(this.siroRajjuNatchathirangal).contains(Integer.valueOf(i)) && Arrays.asList(this.siroRajjuNatchathirangal).contains(Integer.valueOf(i2))) {
            return false;
        }
        if (Arrays.asList(this.kandaRajjuNatchathirangal).contains(Integer.valueOf(i)) && Arrays.asList(this.kandaRajjuNatchathirangal).contains(Integer.valueOf(i2))) {
            return false;
        }
        if (Arrays.asList(this.udhaaraRajjuNatchathirangal).contains(Integer.valueOf(i)) && Arrays.asList(this.udhaaraRajjuNatchathirangal).contains(Integer.valueOf(i2))) {
            return false;
        }
        if (Arrays.asList(this.ooruRajjuNatchathirangal).contains(Integer.valueOf(i)) && Arrays.asList(this.ooruRajjuNatchathirangal).contains(Integer.valueOf(i2))) {
            return false;
        }
        if (Arrays.asList(this.paadhaRajjuNatchathirangal).contains(Integer.valueOf(i)) && Arrays.asList(this.paadhaRajjuNatchathirangal).contains(Integer.valueOf(i2))) {
            return false;
        }
        return true;
    }
}
