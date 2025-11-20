package dataPackage;

import musicItems.Podcast;
import musicItems.PodcastEpisode;
import java.util.ArrayList;
import java.util.List;

public class PodcastData
{
    public static List<Podcast> samplePodcastData()
    {


        // Podcast 1 details
        List<PodcastEpisode> podcast1episodes = new ArrayList<>();
        podcast1episodes.add(new PodcastEpisode("The Appropriation Of Cultures"," Written by Percival Everett. A young Black musician is heckled by a group of white fraternity brothers, asking him to play Dixie. His response surprises everyone, including himself.","33:44","LeVar Burton Reads/The Appropriation of Cultures.wav"));
        podcast1episodes.add(new PodcastEpisode("Mr. Mendoza's Paintbrush", "Written by Luis Alberto Urrea. The self-proclaimed Graffiti King of Mexico brandishes his magical paintbrush, providing commentary on small-town sins.","39:13","LeVar Burton Reads/Mr. Mendozas Paintbrush.wav"));
        podcast1episodes.add(new PodcastEpisode("Quarraopts Can't dance", "Written by Rodrigo Culagovuski. A human street performer breaks out his boombox for an unauthorized and unwelcome performance on Planet Drx.","33:29","LeVar Burton Reads/Quarropts Cant Dance.wav"));

        Podcast podcast1 = new Podcast("LeVar Burton Reads","LeVar Burton,Stitcher","Storytelling","2025-05-09","Hand picked short fictions",podcast1episodes);



        // Podcast 2 details
        List<PodcastEpisode> podcast2episodes = new ArrayList<>();
        podcast2episodes.add(new PodcastEpisode("Unexpectedly Out of a Job? Here's How to Bounce Back","AI that can outperform humans, many people are rightly worried about how those forces might disrupt their careers.","28:37","HRD - Idea Cast/Unexpectedly Out of a Job.wav"));
        podcast2episodes.add(new PodcastEpisode("The Consumer Psychology of Adopting AI","Why People Resist Embracing AI ?","27:50","HRD - Idea Cast/The Consumer Psychology of Adopting AI.wav"));
        podcast2episodes.add(new PodcastEpisode("When Sales Incentives Backfire","How Salespeople Game the System ?","24:51","HRD - Idea Cast/When Sales Incentives Backfire.wav"));

        Podcast podcast2 = new Podcast("HBR - Idea Cast","Alison Beard,Curt Nickisch","Carrer","2025-05-09","Done by Harvard Business Review",podcast2episodes);



        // podcast 3 details
        List<PodcastEpisode> podcast3episodes = new ArrayList<>();
        podcast3episodes.add(new PodcastEpisode("How to reinvent your life ?","Help you deal with stress more effectively, build your resilience, improve your mental wellbeing and transform your physical health.","24:59","Feel Better, Live More/How To Reinvent Your Life.wav"));
        podcast3episodes.add(new PodcastEpisode("How to future proof your brain ?","What if the health of your brain determined the health of every other part of your life – your body, your relationships, even your sense of purpose? Today’s guest firmly believes that it does.","1:34:36","Feel Better, Live More/How To Future-Proof Your Brain.wav"));
        podcast3episodes.add(new PodcastEpisode("How smartphones are rewiring our brains ?","Introduction of social media and smartphones into all aspects of our lives – and what impact this is having on us individually, collectively.","25:44","Feel Better, Live More/How Smartphones Are Rewiring Our Brains.wav"));
        podcast3episodes.add(new PodcastEpisode("Why medicine has gone so far ?","Why early detection and treatment aren't always better, particularly when it turns healthy people into patients decades before they might develop symptoms ?","1:53:15","Feel Better, Live More/Why Medicine Has Gone Too Far.wav"));
        podcast3episodes.add(new PodcastEpisode("The bitter truth about sugar","Why eating too much sugar can be so damaging for our health and shares some practical strategies that we can all use to help ?","24:56","Feel Better, Live More/The Bitter Truth About Sugar.wav"));

        Podcast podcast3 = new Podcast("Feel Better,Live More","Dr. Ranjan Chatterjee","Health","2025-05-09","Health has become overcomplicated. I aim to simplify it.",podcast3episodes);



        List<Podcast> samplePodcasts = new ArrayList<>();
        samplePodcasts.add(podcast1);
        samplePodcasts.add(podcast2);
        samplePodcasts.add(podcast3);

        return samplePodcasts;


    }

}
