package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jdk.jfr.Timestamp;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryMock,secondaryMock;
  @BeforeEach
  public void init(){
    
    primaryMock = mock(TorpedoStore.class);
    secondaryMock = mock(TorpedoStore.class);


    this.ship = new GT4500(primaryMock,secondaryMock);
  }

  @Test
  public void primary_Fire_Success()
  {
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, result);
    verify(primaryMock,times(1)).fire(1);
    verify(primaryMock,times(1)).isEmpty();

  }

  @Test
  public void primary_Secondary_Empty()
  {
    ship.setWasPrimaryFiredLast(false);
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(true);
    
    
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(false, result);
    
    verify(primaryMock,times(1)).isEmpty();
    verify(secondaryMock,times(1)).isEmpty();


  }
  @Test
  public void primary_Fire_Failure()
  {
    
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(false);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(false, result);
    verify(primaryMock,times(1)).fire(1);
    verify(primaryMock,times(1)).isEmpty();

  }

  @Test
  public void primary_Empty_Secondary_Success()
  {
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, result);
    verify(secondaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).isEmpty();


  }
  @Test
  public void primary_NotEmpty_Secondary_Empty()
  {
    when(primaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.isEmpty()).thenReturn(true);
   
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, result);
  
    verify(secondaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).isEmpty();


  }
  @Test
  public void primary_Empty_Secondary_NotEmpty()
  {
    
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(false);
   
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, result);
  
    
    verify(primaryMock,times(1)).isEmpty();


  }

  @Test
  public void primary_Last_Secondary_Fire_Success()
  {
   ship.setWasPrimaryFiredLast(true); 
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);
   
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, result);
  
    verify(secondaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).isEmpty();


  }

  @Test
  public void primary_Last_Secondary_Empty_Primary_Success()
  {
   ship.setWasPrimaryFiredLast(true); 
    when(secondaryMock.isEmpty()).thenReturn(true);
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);
   
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, result);
  
    verify(primaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).isEmpty();


  }
  @Test
  public void primary_And_Secondary_Failure()
  {
   
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(false);
   
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, result);
  
    verify(primaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).isEmpty();


  }
  @Test
  public void primary_Success_Secondary_Failure()
  {
   
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);
    when(secondaryMock.fire(1)).thenReturn(false);
   
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, result);
  
    verify(primaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).isEmpty();


  }
  @Test
  public void primary_Failure_Secondary_Success()
  {
   
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);
   
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, result);
  
    verify(primaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).isEmpty();


  }

  @Test
  public void primary_Secondary_Fire_Success()
  {
    when(primaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);
    when(secondaryMock.fire(1)).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    assertEquals(true, result);
    verify(secondaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).isEmpty();
    verify(primaryMock,times(1)).fire(1);
  }
  @Test
  public void fireTorpedo_Single_Success(){
    when(primaryMock.fire(1)).thenReturn(true);
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMock,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(true);
    when(secondaryMock.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMock,times(1)).fire(1);
    verify(secondaryMock,times(1)).fire(1);
  }

}
