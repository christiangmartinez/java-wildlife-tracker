import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class EndangeredAnimalTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void endangeredAnimal_instantiatesCorrectly_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void getters_returnMemberVariables_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertEquals("Fox", testEndangeredAnimal.getName());
    assertEquals("Healthy", testEndangeredAnimal.getHealth());
    assertEquals("Young", testEndangeredAnimal.getAge());
  }

  @Test
  public void equals_returnsTrueIfNameIsTheSame_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    EndangeredAnimal otherEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertTrue(testEndangeredAnimal.equals(otherEndangeredAnimal));
  }

  @Test
  public void save_assignsIdAndSavesObjectToDatabase() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    EndangeredAnimal savedEndangeredAnimal = EndangeredAnimal.all().get(0);
    assertEquals(testEndangeredAnimal.getId(), savedEndangeredAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Badger", "Okay", "Adult");
    secondEndangeredAnimal.save();
    assertEquals(true, EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
    assertEquals(true, EndangeredAnimal.all().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Badger", "Okay", "Adult");
    secondEndangeredAnimal.save();
    assertEquals(EndangeredAnimal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
  }

  @Test
  public void update_updatesHealthAttribute_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    testEndangeredAnimal.updateHealth("ill");
    assertEquals("ill", EndangeredAnimal.find(testEndangeredAnimal.getId()).getHealth());
  }

  @Test
  public void update_updatesAgeAttribute_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    testEndangeredAnimal.updateAge("Adult");
    assertEquals("Adult", EndangeredAnimal.find(testEndangeredAnimal.getId()).getAge());
  }

  @Test
  public void delete_deletesEnangeredAnimalFromDatabase() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    testEndangeredAnimal.delete();
    assertEquals(0, EndangeredAnimal.all().size());
  }

  @Test
  public void getSightings_returnsAssociatedSighting() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    Sighting testSighting = new Sighting (testEndangeredAnimal.getId(), "45.472428, -121.946466", "Ranger Avery");
    testSighting.save();
    assertEquals(1, testEndangeredAnimal.getSightings().size());
  }

}
