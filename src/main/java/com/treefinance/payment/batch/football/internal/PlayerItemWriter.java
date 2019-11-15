package com.treefinance.payment.batch.football.internal;

import com.treefinance.payment.batch.football.Player;
import com.treefinance.payment.batch.football.PlayerDao;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @author lxp
 * @date 2019/10/25 下午4:34
 * @Version 1.0
 */
public class PlayerItemWriter implements ItemWriter<Player> {

    private PlayerDao playerDao;

    @Override
    public void write(List<? extends Player> players) throws Exception {
        for (Player player : players) {
            playerDao.savePlayer(player);
        }
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

}
