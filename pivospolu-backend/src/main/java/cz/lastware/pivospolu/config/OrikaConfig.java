package cz.lastware.pivospolu.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Configuration
public class OrikaConfig {

	@Bean
	public MapperFactory mapperFactory() {
		return new DefaultMapperFactory.Builder()
				.build();
	}

	@Bean
	public MapperFacade mapper() {
		return mapperFactory().getMapperFacade();
	}

	@PostConstruct
	public void registerConverters() {
		ConverterFactory converterFactory = mapperFactory().getConverterFactory();
		converterFactory.registerConverter(new ZonedDateTimeToLocalDateTimeConverter());
	}

	private class ZonedDateTimeToLocalDateTimeConverter
			extends BidirectionalConverter<ZonedDateTime, LocalDateTime> {
		@Override
		public LocalDateTime convertTo(ZonedDateTime source, Type<LocalDateTime> destinationType,
				MappingContext mappingContext) {
			return LocalDateTime.from(source);
		}

		@Override
		public ZonedDateTime convertFrom(LocalDateTime source, Type<ZonedDateTime> destinationType,
				MappingContext mappingContext) {
			return source.atZone(ZoneOffset.UTC);
		}
	}
}
