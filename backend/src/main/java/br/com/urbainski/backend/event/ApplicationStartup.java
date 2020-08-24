package br.com.urbainski.backend.event;

import br.com.urbainski.backend.job.ConsultaSefazStatusJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Cristian Urbainski
 * @since 23/08/2020
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final Scheduler scheduler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        JobDetail jobDetail = JobBuilder.newJob(ConsultaSefazStatusJob.class)
                .withIdentity(UUID.randomUUID().toString(), "consulta-sefaz-jobs")
                .build();

        boolean exists = checkExists(jobDetail.getKey());

        if (exists) {

            log.info("Job de consulta de status da sefaz já configurado...");
            return;
        }

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("consulta-sefaz-trigger")
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(5))
                .build();

        try {

            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Job de consulta de status da sefaz agendado com sucesso...");
        } catch (SchedulerException ex) {

            log.error("Falha ao agendar job de consulta de status da sefaz", ex);
        }
    }

    private boolean checkExists(JobKey jobKey) {

        try {

            return scheduler.checkExists(jobKey);
        } catch (SchedulerException ex) {

            log.error("Falha ao consultar se o job exists", ex);
            return true;
        }
    }

}