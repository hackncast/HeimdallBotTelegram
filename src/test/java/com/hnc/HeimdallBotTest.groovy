package com.hnc

import org.junit.Before
import org.junit.Test
/**
 * Created by samuel on 11/07/17.
 */
class HeimdallBotTest {

    HeimdallBot heimdallBot;

    @Before
    public void init() {
        heimdallBot = new HeimdallBot();
    }

    @Test
    public void buscaValorXPTest() {

//        Assert.assertEquals("resultado", heimdallBot.buscaValorXP("20 XP"), 20);
//        Assert.assertEquals("resultado", heimdallBot.buscaValorXP("VE GANHO 100XP"), 100);
//        Assert.assertEquals("resultado", heimdallBot.buscaValorXP("VOCE GANHOU 100 XP"), 100);
//        Assert.assertEquals("resultado", heimdallBot.buscaValorXP("VOCE GANHOU -100 XP"), -100);

        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            println r.nextInt();
        }

    }


}
