
delete from airport;

insert into airport(short_name, full_name, country, city, slat, slon)values('TIA','Tirana International Airport', 'Albania', 'Tirana','41.4147', '19.7206');
insert into airport(short_name, full_name, country, city, slat, slon)values('VIE','Vienna International Airport', 'Austria', 'Vienna','48.0637', '16.3411');
insert into airport(short_name, full_name, country, city, slat, slon)values('MSQ','Minsk National Airport', 'Belarus', 'Minsk','53.90095', '28.03522');
insert into airport(short_name, full_name, country, city, slat, slon)values('ANR','Antwerp International Airport', 'Belgium','Antwerpen' ,'51.1887', '4.4609');
insert into airport(short_name, full_name, country, city, slat, slon)values('SOF','Sofia Airport', 'Bulgaria','Sofia' ,'42.6934', '23.4071');
insert into airport(short_name, full_name, country, city, slat, slon)values('VAR','Varna Airport', 'Bulgaria','Varna' ,'43.2368', '27.8271');
insert into airport(short_name, full_name, country, city, slat, slon)values('BWK','Brač Airport', 'Croatia','Brac' ,'43.28583', '16.6797');
insert into airport(short_name, full_name, country, city, slat, slon)values('LCA','Larnaca International Airport', 'Cyprus','Larnaca' ,'34.8723', '33.6203');
insert into airport(short_name, full_name, country, city, slat, slon)values('PRG','Václav Havel Airport Prague', 'Czech Republic','Prague' ,'50.1018', '14.2632');
insert into airport(short_name, full_name, country, city, slat, slon)values('CPH','Copenhagen Airport, Kastrup', 'Denmark','Copenhagen' ,'55.6180', '12.6508');
insert into airport(short_name, full_name, country, city, slat, slon)values('EPU','Pärnu Airport', 'Estonia','Parnu' ,'58.4216', '24.4659');
insert into airport(short_name, full_name, country, city, slat, slon)values('JYV','Jyväskylä Airport', 'Finland', 'Jyväskylä','62.2403', '25.4022');
insert into airport(short_name, full_name, country, city, slat, slon)values('FKB','Karlsruhe/Baden-Baden Airport', 'Germany', 'Baden-Baden','48.7782', '8.0881');
insert into airport(short_name, full_name, country, city, slat, slon)values('BER','Berlin Brandenburg Airport', 'Germany', 'Berlin','52.3644', '13.5099');
insert into airport(short_name, full_name, country, city, slat, slon)values('AXD','Alexandroupolis International Airport', 'Greece', 'Alexandroupolis','40.8558', '25.95626');
insert into airport(short_name, full_name, country, city, slat, slon)values('KIT','Kithira Island National Airport', 'Greece','Kithira' ,'36.2742', '23.016976');
insert into airport(short_name, full_name, country, city, slat, slon)values('KEF','Keflavík International Airport', 'Iceland', 'Keflavík','63.9786', '22.6350');


delete from profession;
insert into profession(name, rank, hour_rate ) values('air hostess', 'middle' , 15.0)
insert into profession(name, rank, hour_rate ) values('air hostess', 'senior' , 20.0)
insert into profession(name, rank, hour_rate ) values('pilot', 'senior' , 40.0)
insert into profession(name, rank, hour_rate ) values('pilot', 'middle' , 30.0)
insert into profession(name, rank, hour_rate ) values('navigator', 'middle' , 30.0)
insert into profession(name, rank, hour_rate ) values('radioman', 'middle' , 30.0)


delete from personnel;

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Mike','Vazovsky', prof.id , '20.12.1985', 1 from profession prof where prof.name='navigator' and prof.rank='middle';


insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Nick','Vazovsky', prof.id , '10.02.1980', 1 from profession prof where prof.name='navigator' and prof.rank='middle';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Petr','Johnson', prof.id , '01.01.1979', 1 from profession prof where prof.name='pilot' and prof.rank='senior';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Keyth','Klinton', prof.id , '09.04.1979', 0 from profession prof where prof.name='pilot' and prof.rank='middle';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Alex','Jezoff', prof.id , '02.04.1995', 1 from profession prof where prof.name='pilot' and prof.rank='middle';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'John','Smith', prof.id , '09.04.1993', 1 from profession prof where prof.name='pilot' and prof.rank='middle';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Jess','Boyden', prof.id , '22.09.1989', 0 from profession prof where prof.name='air hostess' and prof.rank='middle';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Emily','Spark', prof.id , '20.10.1994', 0 from profession prof where prof.name='air hostess' and prof.rank='senior';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Sandra','Nasik', prof.id , '22.09.1992', 0 from profession prof where prof.name='air hostess' and prof.rank='middle';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'Alehandro','Pereiro', prof.id , '21.07.1990', 1 from profession prof where prof.name='radioman' and prof.rank='middle';

insert into personnel(first_name, last_name, profession_id, birth_date, gender)  select 'John','Malkovich', prof.id , '14.04.1985', 1 from profession prof where prof.name='radioman' and prof.rank='middle';

delete from crew_types;
insert into crew_types(name)values('full');
insert into crew_types(name)values('clipped');
delete from rl_crew_professions;

insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='pilot' and prof.rank='middle' and crew.name='full'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='pilot' and prof.rank='senior' and crew.name='full'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='navigator' and prof.rank='middle' and crew.name='full'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='air hostess' and prof.rank='middle' and crew.name='full'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='air hostess' and prof.rank='middle' and crew.name='full'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='air hostess' and prof.rank='senior' and crew.name='full'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='radioman' and prof.rank='middle' and crew.name='full'

insert into rl_crew_professions(crew_id, profession_id) select crew.id, prof.id from profession prof, crew_types crew where prof.name='pilot' and prof.rank='middle' and crew.name='clipped'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='navigator' and prof.rank='middle' and crew.name='clipped'
insert into rl_crew_professions(crew_id, profession_id) select  crew.id, prof.id from profession prof, crew_types crew where prof.name='air hostess' and prof.rank='middle' and crew.name='clipped'

delete from flights;
insert into flights(departure_time, dest_time, crew_type_id, departure_airport_id, dest_airport_id) select '2021-02-10 10:30:00', '2021-02-10 15:22:00', ct.id, dep.id, dest.id from crew_types ct, airport dest, airport dep  where ct.name='full' and dep.short_name='MSQ' and dest.short_name = 'VAR'
insert into flights(departure_time, dest_time, crew_type_id, departure_airport_id, dest_airport_id) select '2021-02-10 18:30:00', '2021-02-10 23:42:00', ct.id, dep.id, dest.id from crew_types ct, airport dest, airport dep  where ct.name='full' and dep.short_name='VAR' and dest.short_name = 'MSQ'
insert into flights(departure_time, dest_time, crew_type_id, departure_airport_id, dest_airport_id) select '2021-02-12 11:45:00', '2021-02-12 18:10:00', ct.id, dep.id, dest.id from crew_types ct, airport dest, airport dep  where ct.name='full' and dep.short_name='MSQ' and dest.short_name = 'KIT'
insert into flights(departure_time, dest_time, crew_type_id, departure_airport_id, dest_airport_id) select '2021-02-12 22:45:00', '2021-02-13 02:12:00', ct.id, dep.id, dest.id from crew_types ct, airport dest, airport dep  where ct.name='full' and dep.short_name='KIT' and dest.short_name = 'MSQ'