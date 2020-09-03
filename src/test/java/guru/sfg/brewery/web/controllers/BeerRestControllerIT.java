package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void voidDeleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/2a519cf5-3d87-483d-92e7-a3964432899d")
                .header("Api-Key", "spring").header("Api-Secret", "spring")).andExpect(status().isOk());
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {

        mockMvc.perform(get("/api/v1/beer/2a519cf5-3d87-483d-92e7-a3964432899d"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerUpc() throws Exception {
        mockMvc.perform((get("/api/v1/beerUpc/0631234200036")))
                .andExpect(status().isOk());
    }

}
