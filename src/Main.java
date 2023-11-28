class Hero {
    private final double level;
    private double baseHp;
    private double baseDef;
    private final double baseMana;
    private double baseRunSpeed;
    private Sword equippedSword;
    private Shield equippedShield;

    public Hero(double level, double baseHp, double baseDef, double baseMana, double baseRunSpeed) {
        this.level = level;
        this.baseHp = baseHp;
        this.baseDef = baseDef;
        this.baseMana = baseMana;
        this.baseRunSpeed = baseRunSpeed;
        this.equippedSword = null;
        this.equippedShield = null;
        updateStats();
    }

    public void equipSword(Sword sword) {
        this.equippedSword = sword;
        updateStats();
    }

    public void equipShield(Shield shield) {
        this.equippedShield = shield;
        updateStats();
    }

    public void PVP(Monster monster) {
        printStats();
        monster.printMonsterStats();
        System.out.println();

        double damageDealt = CalculateAtk();
        monster.DmgTaken(damageDealt);
        System.out.println("JohnSmith dealt " + damageDealt + " damage to the Monster.");

        double damageTaken = monster.CalculateAtk()-baseDef;
        DmgTaken(damageTaken);
        System.out.println("JohnSmith took " + damageTaken + " damage from the Monster.");
        System.out.println();
    }

    private double CalculateAtk() {
        double Atk = 20;
        if (equippedSword != null) {
            Atk += equippedSword.getBaseAtk() * (1 + 0.1 * level);
        }

        return Atk;
    }

    private void updateStats() {
        double runSpeed = baseRunSpeed;
        runSpeed -= (equippedSword != null ? equippedSword.getRunSpeedDecrease() * (0.1 + 0.04 * level) : 0);
        runSpeed -= (equippedShield != null ? equippedShield.getRunSpeedDecrease() * (0.1 + 0.08 * level) : 0);
        this.baseRunSpeed = runSpeed;
        if (equippedShield != null) {
            this.baseDef += equippedShield.getBaseDef();
        }
    }

    public void DmgTaken(double damage) {
        double actualDmg = Math.max(0, damage - getMaxDef());
        this.baseHp -= actualDmg;
    }

    public void printStats() {
        double calculatedSpeed = baseRunSpeed;

        if (equippedSword != null) {
            calculatedSpeed -= equippedSword.getRunSpeedDecrease() -1;
        }

        if (equippedShield != null) {
            calculatedSpeed -= equippedShield.getRunSpeedDecrease() -1;
        }

        System.out.println("JohnSmith Lv: " + level + " | Atk: " + CalculateAtk() +
                " | HP: " + getMaxHp() + " | Def: " + getMaxDef() + " | Mana: " + getMaxMana() +
                "| BaseSpeed: " + calculatedSpeed);
    }


    private double getMaxHp() {
        return baseHp + (20 * level);
    }
    public double getBaseHp() {
        return baseHp;
    }

    private double getMaxMana() {
        return baseMana + 2 * level;
    }

    private double getMaxDef() {
        return baseDef;
    }

    public static void main(String[] args) {
        Sword sword = new Sword(990, 1);
        Shield shield = new Shield(100, 1);

        Monster monster = new Monster(100, 5000);
        Hero johnSmith = new Hero(10, 1000, 0, 5, 5);

        johnSmith.PVP(monster);
        System.out.println();
        System.out.println("BUFF WITH OP MUSIC AND GET LEGENDARY EQUIPMENT!!!!!");
        System.out.println();
        johnSmith.equipSword(sword);
        johnSmith.equipShield(shield);

        while (johnSmith.getBaseHp() > 0 && monster.getMonsterHp() > 0) {
            johnSmith.PVP(monster);

            if (johnSmith.getBaseHp() <= 0) {
                System.out.println();
                System.out.println("JohnSmith Hp: 0");
                System.out.println("JohnSmith has been defeated!");
                break;
            }

            if (monster.getMonsterHp() <= 0) {
                System.out.println();
                System.out.println("Monster Hp: 0.0 "+"JohnSmith Hp:" +johnSmith.getBaseHp());
                System.out.println("JohnSmith is victory!");
                break;
            }
        }


    }
}

record Sword(double baseAtk, double runSpeedDecrease) {
    public double getBaseAtk() {
        return baseAtk;
    }

    public double getRunSpeedDecrease() {
        return runSpeedDecrease;
    }
}

record Shield(double baseDef, double runSpeedDecrease) {
    public double getBaseDef() {
        return baseDef;
    }

    public double getRunSpeedDecrease() {
        return runSpeedDecrease;
    }
}

class Monster {
    private final double monsterLv;
    private double monsterHp;

    public Monster(double monsterLv, double monsterHp) {
        this.monsterLv = monsterLv;
        this.monsterHp = monsterHp;
    }

    public double CalculateAtk() {
        double baseAttack = 0;
        return baseAttack + (monsterLv * 3);
    }

    public void DmgTaken(double damage) {
        double actualDmg = Math.max(0, damage);
        this.monsterHp -= actualDmg;
        if(monsterHp<0){
            this.monsterHp=0;
        }
    }

    public void printMonsterStats() {
        System.out.println("Monster   Lv: " + monsterLv + " | Atk: " + CalculateAtk() + " | HP: " + monsterHp + " |");
    }

    public double getMonsterHp() {
        return monsterHp;
    }
}