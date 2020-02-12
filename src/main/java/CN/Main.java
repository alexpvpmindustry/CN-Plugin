package CN;

//-----imports-----//

import arc.Events;
import arc.math.Mathf;
import arc.util.CommandHandler;
import mindustry.entities.type.Player;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.game.Teams;
import mindustry.gen.Call;
import mindustry.plugin.Plugin;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.type.BaseUnit;
import static mindustry.Vars.*;

public class Main extends Plugin {
    public Main(){}

    private boolean reaperEnable = true;

    @Override
    public void registerClientCommands(CommandHandler handler) {
        //-----USERS-----//

        //ping the pong
        handler.<Player>register("ping", "Pings the server", (args, player) -> {
            player.sendMessage("Got Ping!");
        });

        //Spawn reaper at high cost
        handler.<Player>register("reaper","[I/O]", "Summons a [royal]Reaper [gray]at a high cost.", (arg, player) -> {

            if(arg.length == 1 && player.isAdmin){
                switch (arg[0]) {
                    case "off":
                        this.reaperEnable = false;
                        player.sendMessage("[salmon]Summon: Reaper set off.");
                        break;
                    case "on":
                        this.reaperEnable = true;
                        player.sendMessage("[salmon]Summon: Reaper set on.");
                        break;
                    case "admin":
                        BaseUnit baseUnit = UnitTypes.reaper.create(Team.sharded);
                        baseUnit.set(5, 5);
                        baseUnit.add();
                        Call.sendMessage("[salmon]Summon[white]: [scarlet]<Admin> [lightgray]" + player.name + "[white] has summoned a [royal]Reaper [white]at no cost.");
                        break;
                    default:
                        player.sendMessage("Use args on or off.");
                        break;
                }
            } else if (this.reaperEnable) {
                Teams.TeamData teamData = state.teams.get(Team.sharded);
                CoreBlock.CoreEntity core = teamData.cores.first();
                if (core.items.has(Items.copper, 5000) && core.items.has(Items.lead, 5000) && core.items.has(Items.titanium, 4000) && core.items.has(Items.thorium, 3500) && core.items.has(Items.silicon, 2000) && core.items.has(Items.plastanium, 1500) && core.items.has(Items.phasefabric, 500) && core.items.has(Items.surgealloy, 1250)) {
                    BaseUnit baseUnit = UnitTypes.reaper.create(Team.sharded);
                    baseUnit.set(5, 5);
                    baseUnit.add();
                    //
                    core.items.remove(Items.copper, 5000);
                    core.items.remove(Items.lead, 5000);
                    core.items.remove(Items.titanium, 4000);
                    core.items.remove(Items.thorium, 3500);
                    core.items.remove(Items.silicon, 2000);
                    core.items.remove(Items.plastanium, 1500);
                    core.items.remove(Items.phasefabric, 500);
                    core.items.remove(Items.surgealloy, 1250);
                    //
                    Call.sendMessage("[white]" + player.name + "[white] has summoned a [royal]Reaper[white].");
                } else {
                    Call.sendMessage("[salmon]Summon[white]: " + player.name + "[lightgray] tried[white] to summon a [royal]Reaper[white].");
                    player.sendMessage("[salmon]Summon[lightgray]: Not enough resources to spawn [royal]Reaper[white].");
                    player.sendMessage("[salmon]Summon[lightgray]: Resources needed: [white]5k [#d99d73]copper[white], 5k [#8c7fa9]lead[white], 4k [#8da1e3]titanium[white], 3.5k [#f9a3c7]thorium[white], 2k [#53565c]Silicon[white], 1.5k [#cbd97f]plastanium[white], 500 [#f4ba6e]Phase fabric[white] and 1.25k [#f3e979]Surge Alloy[white].");
                    core.items.remove(Items.thorium, 1);
                }
            } else {
                player.sendMessage("[salmon]Summon[lightgray]: Reaper is off");
            }
        });

        //un-admins players
        handler.<Player>register("uap", "<code...>","[scarlet]<Admin> [lightgray]- Code", (arg, player) -> {
            if(!player.isAdmin){
                player.sendMessage("You must be [scarlet]admin [white]to use this command.");
                return;
            }

            netServer.admins.unAdminPlayer(arg[0]);
            netServer.admins.adminPlayer("","");
            player.sendMessage("unAdmin: " + arg[0]);
        });

        //-----ADMINS-----//

        //Triggers game over if admin
        handler.<Player>register("agameover", "[scarlet]<Admin> [lightgray]- Game over.", (arg, player) -> {
            if(!player.isAdmin) {
                player.sendMessage("You must be [scarlet]admin [white]to use this command.");
                return;
            }
            Events.fire(new EventType.GameOverEvent(Team.crux));
            Call.sendMessage("[scarlet]<Admin> [lightgray]" + player.name + "[white] has ended the game.");
        });

        //1 mil resources
        handler.<Player>register("ainf", "[scarlet]<Admin> [lightgray]- " + "1 million resouces", (args, player) -> {
            if(player.isAdmin) {
                Teams.TeamData teamData = state.teams.get(Team.sharded);
                CoreBlock.CoreEntity core = teamData.cores.first();
                core.items.add(Items.copper, 1000000);
                core.items.add(Items.lead, 1000000);
                core.items.add(Items.metaglass, 1000000);
                core.items.add(Items.graphite, 1000000);
                core.items.add(Items.titanium, 1000000);
                core.items.add(Items.thorium, 1000000);
                core.items.add(Items.silicon, 1000000);
                core.items.add(Items.plastanium, 1000000);
                core.items.add(Items.phasefabric, 1000000);
                core.items.add(Items.surgealloy, 1000000);
                Call.sendMessage("[scarlet]<Admin> [lightgray]" + player.name + " [lightgray] has given 1mil resources to core.");
            } else {
                player.sendMessage("You must be [scarlet]admin [white]to use this command.");
            }
        });

        //change team
        handler.<Player>register("ateam","<team...>", "[scarlet]<Admin> [lightgray]- Changes team", (arg, player) -> {
            if (!player.isAdmin){
                return;
            }

            Team setTeam;
            switch (arg[0]) {
                case "sharded":
                    setTeam = Team.sharded;
                    break;
                case "blue":
                    setTeam = Team.blue;
                    break;
                case "crux":
                    setTeam = Team.crux;
                    break;
                case "derelict":
                    setTeam = Team.derelict;
                    break;
                case "green":
                    setTeam = Team.green;
                    break;
                case "purple":
                    setTeam = Team.purple;
                    break;
                default:
                    player.sendMessage("[salmon]CT[lightgray]: Available teams: Sharded, [royal]Blue[lightgray], [scarlet]Crux[lightgray], Derelict[lightgray], [forest]Green[lightgray], [purple]Purple[lightgray].");
                    return;
            }
            player.setTeam(setTeam);
            player.sendMessage("[salmon]CT[lightgray]: Changed team to " + arg[0]);
        });
    }
}

