package core;

public class Log {

    private static String[][] general;
    private static String[][] damageLog;
    private static Timer damageLogTimer;
    private static String[][] damageFloatingText;
    private static Timer damageFloatingTextTimer;
    private static String[][] criticalDamageFloatingText;
    private static Timer criticalDamageFloatingTextTimer;

    public Log() {
        general = new String[100][2];
        damageLog = new String[100][7];
        damageLogTimer = new Timer(1);
        damageFloatingText = new String[60][7];
        damageFloatingTextTimer = new Timer(0.5);
        criticalDamageFloatingText = new String[4][7];
        criticalDamageFloatingTextTimer = new Timer(0);
    }

    public void update() {
        damageLogTimer.update();
        damageFloatingTextTimer.update();
        criticalDamageFloatingTextTimer.update();

        updateDamageFloatingText();
        updateCriticalDamageFloatingText();
    }

    public void addToGeneral(String type, String string) {
        if (general[0][0] != null) {
            for (int i = general.length - 1; i > 0; i--) {
                if(general[i - 1][0] != null) {
                    for (int j = 0; j < 2; j++) {
                        general[i][j] = general[i - 1][j];
                    }
                }
            }
        }

        general[0][0] = type;
        general[0][1] = string;
    }

    public void addToDamageLog(String attacker, String defender, String x, String y, String type, String damage, String damageType) {
        if (damageLog[0][0] != null) {
            for (int i = damageLog.length - 1; i > 0; i--) {
                if(damageLog[i - 1][0] != null) {
                    for (int j = 0; j < 7; j++) {
                        damageLog[i][j] = damageLog[i - 1][j];
                    }
                }
            }
        }

        damageLog[0][0] = attacker;
        damageLog[0][1] = defender;
        damageLog[0][2] = x;
        damageLog[0][3] = y;
        damageLog[0][4] = type;
        damageLog[0][5] = damage;
        damageLog[0][6] = damageType;

        if(damageLog[0][4].equals("Hit") || damageLog[0][4].equals("Miss")){
            addToDamageFloatingText(damageLog);
        }

        if(damageLog[0][4].equals("Critical")){
            addToCriticalDamageFloatingText(damageLog);
        }
    }

    private void addToDamageFloatingText(String[][] damageLog) {
        for (int i = 0; i < 7; i++){
            if (damageFloatingText[0][i] != null) {
                damageFloatingText[1][i] = damageFloatingText[0][i];
            }
            damageFloatingText[0][i] = damageLog[0][i];
        }
    }

    public void addToCriticalDamageFloatingText(String[][] damageLog) {
        for (int i = 0; i < 7; i++){
            if (criticalDamageFloatingText[0][i] != null) {
                criticalDamageFloatingText[1][i] = criticalDamageFloatingText[0][i];
            }

            criticalDamageFloatingText[0][i] = damageLog[0][i];
        }
        criticalDamageFloatingTextTimer.startClock(3);
    }

    private void updateDamageFloatingText() {
        if (damageFloatingTextTimer.timeIsUp()) {
            damageFloatingTextTimer.startClock(0.05);
            for (int i = damageFloatingText.length - 1; i > 0; i--) {
                for (int j = 0; j < 7; j++) {
                    if (i == damageFloatingText.length - 1) {
                        damageFloatingText[i][j] = null;
                    }
                    if (damageFloatingText[i - 1][j] != null) {
                        damageFloatingText[i][j] = damageFloatingText[i - 1][j];
                        damageFloatingText[i - 1][j] = null;
                    }
                }
            }
        }
    }

    private void updateCriticalDamageFloatingText(){
        if (criticalDamageFloatingTextTimer.timeIsUp()) {
            for (int i = 0; i < 7; i++){
                criticalDamageFloatingText[0][i] = null;
            }
        }
    }

    public void clearDamageFloatingText() {
        for (int i = 0; i < 7; i++) {
            damageFloatingText[0][i] = null;
        }

    }

    public String[][] getGeneral() {return general;}
    public String[][] getDamageLog() {return damageLog;}
    public String[][] getDamageFloatingText() {return damageFloatingText;}
    public String[][] getCriticalDamageFloatingText() {return criticalDamageFloatingText;}
    public static Timer getDamageFloatingTextTimer() {return damageFloatingTextTimer;}
}


