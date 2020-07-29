package br.com.rhribeiro25.manageLabs.spring.config;

import br.com.rhribeiro25.manageLabs.spring.batch.ManageLabsFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<Object> itemReader, ItemProcessor<Object, Object> itemProcessor,
                   ItemWriter<Object> itemWriter) {

        Step step = stepBuilderFactory.get("ETL-file-load").<Object, Object>chunk(100).reader(itemReader)
                .processor(itemProcessor).writer(itemWriter).build();

        return jobBuilderFactory.get("ETL-Load").incrementer(new RunIdIncrementer()).start(step).build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Object> itemReader(@Value("#{jobParameters[filePath]}") String filePath) {
        FlatFileItemReader<Object> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(filePath));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(0);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Object> lineMapper() {
        DefaultLineMapper<Object> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        lineTokenizer.setNames(new String[]{"obj", "id", "createdAt", "name", "type/address", "status"});
        ManageLabsFieldSetMapper fieldSetMapper = new ManageLabsFieldSetMapper();
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}
