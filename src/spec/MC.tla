---- MODULE MC ----
EXTENDS TPaxos, TLC

\* MV CONSTANT declarations@modelParameterConstants
CONSTANTS
p1, p2, p3
----

\* MV CONSTANT declarations@modelParameterConstants
CONSTANTS
v1, v2
----

\* MV CONSTANT definitions Participant
const_15824414568362000 == 
{p1, p2, p3}
----

\* MV CONSTANT definitions Value
const_15824414568363000 == 
{v1, v2}
----

\* SYMMETRY definition
symm_15824414568364000 == 
Permutations(const_15824414568362000) \union Permutations(const_15824414568363000)
----

\* CONSTANT definition @modelParameterDefinitions:1
def_ov_15824414568376000 ==
0..2
----
=============================================================================
\* Modification History
\* Created Sun Feb 23 15:04:16 CST 2020 by pure_
