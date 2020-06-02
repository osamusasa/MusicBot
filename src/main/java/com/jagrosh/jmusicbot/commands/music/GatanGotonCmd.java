package com.jagrosh.jmusicbot.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.audio.QueuedTrack;
import com.jagrosh.jmusicbot.commands.MusicCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 *
 * @author Osamu Sasano
 */
public class GatanGotonCmd extends MusicCommand {
    private final String loadingEmoji;

    public GatanGotonCmd(Bot bot) {
        super(bot);
        this.loadingEmoji = bot.getConfig().getLoading();
        this.name = "gatan";
        this.help = "plays the ガタンゴトン song.";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.beListening = true;
        this.bePlaying = false;
    }

    @Override
    public void doCommand(CommandEvent event) {
        if(event.getArgs().isEmpty()) {
            String args = "https://www.youtube.com/watch?v=tyneiz9FRMw";
            event.reply(
                    loadingEmoji+" Loading... `["+args+"]`",
                    m -> bot.getPlayerManager().loadItemOrdered(
                            event.getGuild(),
                            args,
                            new ResultHandler(event)
                    )
            );
        }
    }

    private class ResultHandler implements AudioLoadResultHandler {
        private final CommandEvent event;

        ResultHandler(CommandEvent e) {
            this.event = e;
        }

        @Override
        public void trackLoaded(AudioTrack audioTrack) {
            (
                    (AudioHandler)event
                            .getGuild()
                            .getAudioManager()
                            .getSendingHandler()
            ).addTrack(new QueuedTrack(audioTrack, event.getAuthor()));
        }

        @Override
        public void playlistLoaded(AudioPlaylist audioPlaylist) {

        }

        @Override
        public void noMatches() {

        }

        @Override
        public void loadFailed(FriendlyException e) {

        }
    }
}
