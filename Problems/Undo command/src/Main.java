import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

interface Movable {
    int getX();
    int getY();
    void setX(int newX);
    void setY(int newY);
}

interface Storable {
    int getInventoryLength();
    String getInventoryItem(int index);
    void setInventoryItem(int index, String item);
}


interface Command {
    void execute();
    void undo();
}

class CommandMove implements Command {
    Movable entity;
    int xMovement;
    int yMovement;
    StoreMovableCoordinates coordinates = new StoreMovableCoordinates();
    static int index = 0;

    public CommandMove() { }

    public Movable getEntity() {
        return entity;
    }

    public void setEntity(Movable entity) {
        this.entity = entity;
    }

    public int getxMovement() {
        return xMovement;
    }

    public void setxMovement(int xMovement) {
        this.xMovement = xMovement;
    }

    public int getyMovement() {
        return yMovement;
    }

    public void setyMovement(int yMovement) {
        this.yMovement = yMovement;
    }

    @Override
    public void execute() {
        System.out.println(entity.getX() + " " + entity.getY() + " " + xMovement + " " + yMovement);
        entity.setX(entity.getX() + xMovement);
        entity.setY(entity.getY() + yMovement);
        coordinates.setInventoryItem(index, (xMovement + " " + yMovement));
        //System.out.println("Pass IN " + index + " xMovement/YMovement \u2192 " + xMovement + "/" + yMovement);
        //System.out.println("\nPassed " + index + " x/y \u2192 " + entity.getX() + "/" + entity.getY());
        coordinates.coordinateList.forEach((k,v) -> System.out.println("Stored " + k + " \u2192" + v));
        index++;

    }

    @Override
    public void undo() {
        if (index > 0) {
            String[] xyCoordinates = coordinates.getInventoryItem(index-- - 1).split(" ");
            System.out.println(Arrays.toString((xyCoordinates)));
            entity.setX(entity.getX() - Integer.parseInt(xyCoordinates[0]));
            entity.setY(entity.getY() - Integer.parseInt(xyCoordinates[1]));
            System.out.println("Recover " + index + " x/y \u2192 " + entity.getX() + "/" + entity.getY());

        }
    }
}

class CommandPutItem implements Command {
    Storable entity;
    String item;
    int currentStorageIndex = 0;
    int previousStorageIndex = currentStorageIndex;

    public Storable getEntity() {
        return entity;
    }

    public void setEntity(Storable entity) {
        this.entity = entity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public void execute() {
        for (int storageIndex = 0; storageIndex < entity.getInventoryLength(); storageIndex++) {
            if (entity.getInventoryItem(storageIndex) == null) {
                entity.setInventoryItem(storageIndex, item);
                previousStorageIndex = currentStorageIndex;
                currentStorageIndex = storageIndex;
                break;
            }
        }
    }

    @Override
    public void undo() {
        item = entity.getInventoryItem(currentStorageIndex);
        currentStorageIndex = previousStorageIndex;
        previousStorageIndex = entity.getInventoryLength() - 1;
    }
}

class StoreMovableCoordinates implements Storable {

    Map<Integer, String> coordinateList = new HashMap<>();

    @Override
    public int getInventoryLength() {
        return coordinateList.size();
    }

    @Override
    public String getInventoryItem(int index) {
        return coordinateList.get(index);
    }

    @Override
    public void setInventoryItem(int index, String item) {
        coordinateList.put(index, item);
    }
}

class TestMovable implements Movable{
    int xMovable = 0;
    int yMovable = 0;

    @Override
    public int getX() {
        return xMovable;
    }

    @Override
    public int getY() {
        return yMovable;
    }

    @Override
    public void setX(int newX) {
        this.xMovable = newX;
    }

    @Override
    public void setY(int newY) {
        this.yMovable = newY;
    }
}

//*************TESTING PURPOSE ONLY - COMMENT OUT ON HyperSkill Checking****************\\
//
//}
