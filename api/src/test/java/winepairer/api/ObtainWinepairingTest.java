package winepairer.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.*;
import jakarta.ws.rs.core.*;

import winepairer.api.dto.*;

public class ObtainWinepairingTest {
    @Test
    public void requestingWinepairingShouldBeAllowed() {
        var response = obtainWinepairing("Zalm");
        assertEquals(200, response.getStatus());
    }

    @Test
    public void requestingWinepairingReturnsChardonnay() {
        var response = obtainWinepairing("Zalm");
        var entity = (WinePairing) response.getEntity();
        assertEquals("Chardonnay", entity.getWine());
    }

    private Response obtainWinepairing(String meal) {
        var servlet = new ObtainWinepairing();
        var request = createRequestContext();
        var input = mealInput(meal);
        return servlet.initialize(request, input);
    }

    private HttpServletRequest createRequestContext() {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);
        return request;
    }

    private HttpServletRequest request;
    private HttpSession session;

    private MealInput mealInput(String meal) {
        var input = new MealInput();
        input.setMealToPair(meal);
        return input;
    }
}