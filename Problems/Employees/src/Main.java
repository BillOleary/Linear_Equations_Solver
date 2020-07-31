class Employee {

    // write fields
    protected String name;
    protected String email;
    protected int experience;
    // write constructor
    public Employee() {
        this("Test Name", "test@test.com",10);
    }
    public Employee(String name, String email, int experience) {
        this.name = name;
        this.email = email;
        this.experience = experience;
    }

    // write getters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}

class Developer extends Employee {

    // write fields
    protected String mainLanguage;
    protected String[] skills;

    // write constructor
    public Developer(String name, String email, int experience, String mainLanguage, String[] skill) {
        super(name, email, experience);
        this.mainLanguage = mainLanguage;
        this.skills = skill;
    }

    // write getters

    public String getMainLanguage() {
        return mainLanguage;
    }

    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    public String[] getSkills() {
        return skills.clone();
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
}

class DataAnalyst extends Employee {

    // write fields
    protected boolean phd = false;
    protected String[] methods;

    // write constructor
    public DataAnalyst(String name, String email, int experience, boolean phd, String[] methods) {
        super(name, email, experience);
        this.phd = phd;
        this.methods = methods;
    }

    // write getters

    public boolean isPhd() {
        return phd;
    }

    public void setPhd(boolean phd) {
         this.phd = phd;
    }

    public String[] getMethods() {
        return methods.clone();
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }
}